package com.housservice.housstock.service;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.*;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.model.dto.PlannificationDto;
import com.housservice.housstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlannificationServiceImpl implements PlannificationService {
    private final PlannificationRepository plannificationRepository;
    private final PersonnelRepository personnelRepository;
    private final EtapeRepository etapeRepository;
    private final CommandeClientRepository commandeClientRepository;

    private final MachineRepository machineRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    @Autowired
    public PlannificationServiceImpl(CommandeClientRepository commandeClientRepository,MachineRepository machineRepository,EtapeRepository etapeRepository,PersonnelRepository personnelRepository,MessageHttpErrorProperties messageHttpErrorProperties, PlannificationRepository plannificationRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.plannificationRepository=plannificationRepository;
        this.personnelRepository=personnelRepository;
        this.etapeRepository=etapeRepository;
        this.machineRepository=machineRepository;
        this.commandeClientRepository=commandeClientRepository;

    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllArticle(int page, int size) {
        try {
            calcul();
            List<PlannificationDto> articles = new ArrayList<PlannificationDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Plannification> pageTuts;
            pageTuts = plannificationRepository.findPlannificationByEtat(false,paging);
            articles = pageTuts.getContent().stream().map(article -> {
                return PlannificationMapper.MAPPER.toPlannificationDto(article);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllArticleLance(int page, int size) {
        try {
            calcul();

            List<PlannificationDto> articles = new ArrayList<PlannificationDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Plannification> pageTuts;
            pageTuts = plannificationRepository.findPlannificationByEtat(true,paging);
            articles = pageTuts.getContent().stream().map(article -> {
                return PlannificationMapper.MAPPER.toPlannificationDto(article);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public void calcul() throws ResourceNotFoundException {
        List<Plannification> plannification1 = plannificationRepository.findAll();
        for(Plannification j :plannification1) {
            int total = 0, notready = 0;
            if (!j.getEtapes().isEmpty()) {
                for (PlanEtapes i : j.getEtapes()) {
                    total=total+1;
                    if (i.getTerminer() == true)
                        notready++;
                }


                j.setProgress( (notready * 100) / total);
                if(j.getProgress()!=0){
                    CommandeClient commandeClient = commandeClientRepository.findById(j.getIdComm()).get();
                    commandeClient.setEtat("en production");
                    commandeClientRepository.save(commandeClient);
                }
                if(j.getProgress()==100){
                    CommandeClient commandeClient = commandeClientRepository.findById(j.getIdComm()).get();
                    commandeClient.setEtat("Terminé");
                    commandeClientRepository.save(commandeClient);
                }
                plannificationRepository.save(j);
            }
        }
    }
    @Override
    public void updatePlanification(String id, Plannification plannification) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
     plannificationRepository.delete(plannification1);
     plannification.setEtat(true);
     plannificationRepository.save(plannification);
    }

    @Override
    public void updateEtapes(String id, PlanEtapesDto planEtapesDto) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        List<PlanEtapes> etapes = plannification1.getEtapes();
        CommandeClient commandeClient = commandeClientRepository.findById(plannification1.getIdComm()).get();
        commandeClient.setEtat("Plannifier");
        commandeClientRepository.save(commandeClient);
        if (etapes == null) {
            etapes = new ArrayList<>();
        }

        // Check if the etape exists in the Plannification's etapes list
        boolean etapeExists = false;
        PlanEtapes existingEtapeToRemove = null;
        for (PlanEtapes existingEtape : etapes) {
            if (existingEtape.getNomEtape().equals(planEtapesDto.getNomEtape())) {
                etapeExists = true;
                existingEtapeToRemove = existingEtape;
                break;
            }
        }

        // If the etape exists, remove it from the etapes list
        if (etapeExists) {
            etapes.remove(existingEtapeToRemove);
        }
        planEtapesDto.setRef(plannification1.getRef());
        planEtapesDto.setEtat(false);
        planEtapesDto.setTerminer(false);
        // Add the updated etape
        PlanEtapes etape = PlanEtapesMapper.MAPPER.toPlanEtapes(planEtapesDto);
        etape.setRef(plannification1.getRef());
        etapes.add(etape);
        etape.setQuantiteInitiale(plannification1.getQuantiteInitiale());
        etape.setIdOf(id);
        String[] Nbretapes = this.getEtape(id);
        int length = Nbretapes.length;
        int i = etapes.size();
        if (length== i){
            plannification1.setPlan(true);
            commandeClientRepository.findById(plannification1.getIdComm()).get();
            commandeClient.setEtat("en production");
            commandeClientRepository.save(commandeClient);
        }
        plannification1.setEtapes(etapes);
        plannification1.setNomEtape(etape.getNomEtape());
        plannification1.setQuantiteInitiale(etape.getQuantiteInitiale());
        plannification1.setQuantiteConforme(etape.getQuantiteConforme());
        plannification1.setQuantiteNonConforme(etape.getQuantiteNonConforme());
        plannificationRepository.save(plannification1);
    }


    @Override
    public void updateEtape(String id, Plannification plannification) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));

        plannification1.setNomEtape(plannification.getNomEtape());
        plannification1.setPersonnels(plannification.getPersonnels());
        plannificationRepository.save(plannification1);
    }

    @Override
    public void updateof( Plannification plannification,String id) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
        plannification1.getLigneCommandeClient().setDateLivraison(plannification.getLigneCommandeClient().getDateLivraison());
        plannification1.getLigneCommandeClient().setQuantite(plannification.getLigneCommandeClient().getQuantite());
        plannificationRepository.save(plannification1);
    }
    public String operationType(String etat){
        Etape etape = etapeRepository.findEtapeByNomEtape(etat);
        return etape.getTypeEtape();
    }
    public List<String> getConducteur(String refMachine){
        Machine machine = machineRepository.findMachineByLibelle(refMachine);
        return machine.getNomConducteur();
    }
    public String[] getEtape(String id){
        Plannification plan = plannificationRepository.findById(id).get();
        return plan.getLigneCommandeClient().getProduit().getEtapes();
    }

    public List<String> getMonitrice(){
        List<Personnel> personnel = personnelRepository.findPersonnelByPoste("Monitrice");
        return personnel.stream()
                .map(Personnel::getFullName)
                .collect(Collectors.toList());
    }
    public int indiceEtape(String id) {
        Optional<Plannification> plan = plannificationRepository.findById(id);
        String[] etapes = plan.get().getLigneCommandeClient().getProduit().getEtapes();
        String etapenom = plan.get().getNomEtape();
        List<String> etapesList = Arrays.asList(etapes);
        return etapesList.indexOf(etapenom);
    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

        try {

            List<PlannificationDto> articles;
            Pageable paging = PageRequest.of(page, size);
            Page<Plannification> pageTuts;
            pageTuts = plannificationRepository.findPlannificationByTextToFind(textToFind, paging);
            articles = pageTuts.getContent().stream().map(Plan -> {
                return PlannificationMapper.MAPPER.toPlannificationDto(Plan);
            }).collect(Collectors.toList());
            articles= articles.stream().filter(Plan -> Plan.getEtat()==enVeille).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PlanEtapes getEtapesValue(String id, String Nom) {
        Plannification plannification = plannificationRepository.findById(id).orElse(null);
        if (plannification != null) {
            Optional<PlanEtapes> matchingEtape = plannification.getEtapes().stream()
                    .filter(etape -> etape.getNomEtape().equals(Nom))
                    .findFirst();

            if (matchingEtape.isPresent()) {
                return matchingEtape.get();
            }
        }
        return null;
    }


    public void Suivi(String id) {
        Plannification plannification = plannificationRepository.findById(id).get();
        plannification.setEtat(true);
        plannificationRepository.save(plannification);
    }
    public void deleteArticle(String id){
        Plannification plannification = plannificationRepository.findById(id).get();
        plannificationRepository.delete(plannification);
    }


}
