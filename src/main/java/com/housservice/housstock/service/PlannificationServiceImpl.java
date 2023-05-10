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
    private final MachineRepository machineRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    @Autowired
    public PlannificationServiceImpl(MachineRepository machineRepository,EtapeRepository etapeRepository,PersonnelRepository personnelRepository,MessageHttpErrorProperties messageHttpErrorProperties, PlannificationRepository plannificationRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.plannificationRepository=plannificationRepository;
        this.personnelRepository=personnelRepository;
        this.etapeRepository=etapeRepository;
        this.machineRepository=machineRepository;

    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllArticle(int page, int size) {
        try {
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
    public void updatePlanification(String id, Plannification plannification) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
     plannificationRepository.delete(plannification1);
     if (plannification.getRefMachine()!=null){
         Machine machine = machineRepository.findMachineByLibelle(plannification.getRefMachine());
         machine.setEtat("Reserve");
         machineRepository.save(machine);
     }
     plannification.setEtat(true);
     plannificationRepository.save(plannification);
    }

    @Override
    public void updateEtapes(String id, PlanEtapesDto planEtapesDto) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
        List<PlanEtapes> Etapes = new ArrayList<>();
        plannification1.setNomEtape(planEtapesDto.getNomEtape());
        PlanEtapes Etapes1 = PlanEtapesMapper.MAPPER.toPlanEtapes(planEtapesDto);
        if(plannification1.getEtapes()==null){
            Etapes.add(Etapes1);
            plannification1.setEtapes(Etapes);
            plannificationRepository.save(plannification1);
        }
        Etapes.addAll(plannification1.getEtapes());
        Etapes.add(Etapes1);
        plannification1.setEtapes(Etapes);
        plannificationRepository.save(plannification1);
    }
    @Override
    public void updateEtape(String id, Plannification plannification) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));

        plannification1.setNomEtape(plannification.getNomEtape());
        if (plannification.getRefMachine()!=null){
            plannification1.setRefMachine(plannification.getRefMachine());
        }
        plannification1.setPersonnels(plannification.getPersonnels());
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
        return Arrays.asList(etapes).indexOf(etapenom);

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
}
