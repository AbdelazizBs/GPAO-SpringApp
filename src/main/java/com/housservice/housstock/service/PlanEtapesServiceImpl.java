package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.mapper.PlanEtapesMapper;
import com.housservice.housstock.mapper.PlannificationMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
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

@Service
public class PlanEtapesServiceImpl implements PlanEtapesService{
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final PlanEtapesRepository planEtapesRepository;


    public PlanEtapesServiceImpl(PlanEtapesRepository planEtapesRepository, PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties, PlannificationRepository plannificationRepository, MachineRepository machineRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.planEtapesRepository = planEtapesRepository;

    }
    @Override
    public ResponseEntity<Map<String, Object>> getPlantoday(int page, int size) {
        try {
            List<PlanEtapesDto> planetapes = new ArrayList<PlanEtapesDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<PlanEtapes> pageTuts;
            Date date = new Date();
            pageTuts = planEtapesRepository.findPersonnelByTerminer(true, paging);
            planetapes = pageTuts.getContent().stream().map(personnel -> {
                return PlanEtapesMapper.MAPPER.toPlanEtapesDto(personnel);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("planetapes", planetapes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public void delete(String id) {
    PlanEtapes planEtapes = planEtapesRepository.findById(id).get();
    planEtapesRepository.delete(planEtapes);
    }
    @Override
    public ResponseEntity<Map<String, Object>> searchP(String textToFind, int page, int size, boolean enVeille) {

        try {

            List<PlanEtapesDto> planetapes;
            Pageable paging = PageRequest.of(page, size);
            Page<PlanEtapes> pageTuts;
            pageTuts = planEtapesRepository.findPlanEtapesByTextToFind(textToFind, paging);
            planetapes = pageTuts.getContent().stream().map(Plan -> {
                return PlanEtapesMapper.MAPPER.toPlanEtapesDto(Plan);
            }).collect(Collectors.toList());
            planetapes= planetapes.stream().filter(Plan -> Plan.getTerminer()==enVeille).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("planetapes", planetapes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Map<String, Object>> onSortActivePlanEtapes(int page, int size, String field, String order) {
        try {
            List<PlanEtapesDto> planetapes ;
            Pageable paging = PageRequest.of(page, size);
            Page<PlanEtapes> pageTuts;
            if (order.equals("1")){
                pageTuts = planEtapesRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = planEtapesRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            planetapes = pageTuts.getContent().stream().map(personnel -> {
                return PlanEtapesMapper.MAPPER.toPlanEtapesDto(personnel);
            }).collect(Collectors.toList());
            planetapes =planetapes.stream().filter(personnel -> personnel.getTerminer()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("planetapes", planetapes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
