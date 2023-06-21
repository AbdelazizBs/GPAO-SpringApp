package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.mapper.PlanEtapesMapper;
import com.housservice.housstock.mapper.PlannificationMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.model.dto.PlannificationDto;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.PlanEtapesRepository;
import com.housservice.housstock.repository.PlannificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.repository.util.ReactiveWrapperConverters.map;
@Service
public class AtelierServiceImpl implements AtelierService{
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final PlannificationRepository plannificationRepository;
    private final MachineRepository machineRepository;

    private final PlanEtapesRepository planEtapesRepository;
    private final PersonnelRepository personnelRepository;


    public AtelierServiceImpl( PlanEtapesRepository planEtapesRepository,PersonnelRepository personnelRepository,MessageHttpErrorProperties messageHttpErrorProperties, PlannificationRepository plannificationRepository, MachineRepository machineRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.plannificationRepository = plannificationRepository;
        this.machineRepository = machineRepository;
        this.personnelRepository = personnelRepository;
        this.planEtapesRepository = planEtapesRepository;

    }

    @Override
    public Optional<Machine> getMachineById(String id) {

            return machineRepository.findById(id);

    }




    @Override
    public ResponseEntity<Map<String, Object>> getOfByAteliers(int page, int size,String personnel) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Plannification> pageTuts;
            pageTuts = plannificationRepository.findAll(paging);
            List<PlanEtapes> ateliers = new ArrayList<>();

            for (Plannification planification : pageTuts) {
                if (!planification.getEtapes().isEmpty()) {
                    PlanEtapes firstEtape = planification.getEtapes().get(0);
                    if (firstEtape.getRefMachine() == null && firstEtape.getTerminer()==false && firstEtape.getMonitrice().equals(personnel)) {
                        ateliers.add(firstEtape);
                    }
                }
            }
            Map<String, Object> response = new HashMap<>();
            response.put("ateliers", ateliers);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    @Override
    public void updateEtapes(String id, PlanEtapesDto planEtapesDto) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));

        if (!plannification1.getEtapes().isEmpty()) {
            PlanEtapes firstEtape = plannification1.getEtapes().get(0);

            // Update the properties of the first etape
            if (planEtapesDto.getHeureDebutReel() != null && planEtapesDto.getHeureFinReel() != null) {
                firstEtape.setEtat(true);
            }
            firstEtape.setQuantiteInitiale(planEtapesDto.getQuantiteInitiale());
            firstEtape.setQuantiteConforme(planEtapesDto.getQuantiteConforme());
            firstEtape.setQuantiteNonConforme(planEtapesDto.getQuantiteNonConforme());
            firstEtape.setCommentaire(planEtapesDto.getCommentaire());
            firstEtape.setHeureDebutReel(planEtapesDto.getHeureDebutReel());
            firstEtape.setHeureFinReel(planEtapesDto.getHeureFinReel());
            firstEtape.setDateReel(planEtapesDto.getDateReel());

            plannificationRepository.save(plannification1);
        }
    }
    @Override
    public void terminer(String id,PlanEtapesDto planEtapesDto) throws ResourceNotFoundException {
        Plannification plannification1 = plannificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        List<PlanEtapes> etapes = plannification1.getEtapes();
        if (etapes == null) {
            etapes = new ArrayList<>();
        }
        boolean etapeExists = false;
        PlanEtapes existingEtapeToRemove = null;
        for (PlanEtapes existingEtape : etapes) {
            if (existingEtape.getNomEtape().equals(planEtapesDto.getNomEtape())) {
                etapeExists = true;
                existingEtapeToRemove = existingEtape;
                break;
            }
        }
        if (etapeExists) {
            etapes.remove(existingEtapeToRemove);
        }
        planEtapesDto.setTerminer(true);
        PlanEtapes etape = PlanEtapesMapper.MAPPER.toPlanEtapes(planEtapesDto);
        planEtapesRepository.save(etape);
        etapes.add(etape);
        plannification1.setEtapes(etapes);
        plannificationRepository.save(plannification1);
    }
    public List<String> getMonitrice(){
        List<Personnel> personnel = personnelRepository.findPersonnelByPoste("Operatrice");
        return personnel.stream()
                .map(Personnel::getFullName)
                .collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveAtelier(int page, int size, String field, String order) {
        try {
            Page<Plannification> pageTuts;
            if (order.equals("1")){
                pageTuts = plannificationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = plannificationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            List<PlanEtapes> ateliers = new ArrayList<>();
            for (Plannification planification : pageTuts) {
                if (!planification.getEtapes().isEmpty()) {
                    PlanEtapes firstEtape = planification.getEtapes().get(0);
                    if (firstEtape.getRefMachine() == null && firstEtape.getTerminer()==false) {
                        ateliers.add(firstEtape);
                    }
                }
            }
            Map<String, Object> response = new HashMap<>();
            response.put("ateliers", ateliers);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
