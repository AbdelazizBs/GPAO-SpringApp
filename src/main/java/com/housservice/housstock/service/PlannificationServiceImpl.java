package com.housservice.housstock.service;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.mapper.PlannificationMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.MachineDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     Machine machine = machineRepository.findMachineByLibelle(plannification.getRefMachine());
     machine.setEtat("Reserve");
     machineRepository.save(machine);
     plannification.setEtat(true);
     plannificationRepository.save(plannification);
    }
    public String operationType(String etat){
        Etape etape = etapeRepository.findEtapeByNomEtape(etat);
        return etape.getTypeEtape();
    }
    public List<String> getConducteur(String refMachine){
        Machine machine = machineRepository.findMachineByLibelle(refMachine);
        return machine.getNomConducteur();
    }

}
