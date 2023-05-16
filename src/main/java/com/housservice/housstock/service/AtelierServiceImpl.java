package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.mapper.PlanEtapesMapper;
import com.housservice.housstock.mapper.PlannificationMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.model.dto.PlannificationDto;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PlannificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public AtelierServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, PlannificationRepository plannificationRepository, MachineRepository machineRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.plannificationRepository = plannificationRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public Optional<Machine> getMachineById(String id) {

            return machineRepository.findById(id);

    }

    @Override
    public ResponseEntity<Map<String, Object>> getOfByRefMachine(int page, int size) {
        try {
            List<Plannification> plannifications =  plannificationRepository.findAll();
            Pageable paging = PageRequest.of(page, size);
            Page<Plannification> pageTuts;
            pageTuts = plannificationRepository.findAll(paging);
            List<PlanEtapes> ateliers = new ArrayList<>();

            for (Plannification planification : plannifications) {
                for (PlanEtapes etape : planification.getEtapes()) {
                    if (etape.getRefMachine()==null) {
                        ateliers.add(etape);
                        break;
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
        List<PlanEtapes> etapes = plannification1.getEtapes();
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

        // Add the updated etape
        PlanEtapes etape = PlanEtapesMapper.MAPPER.toPlanEtapes(planEtapesDto);
        etapes.add(etape);
        plannification1.setEtapes(etapes);
        plannificationRepository.save(plannification1);
    }
}
