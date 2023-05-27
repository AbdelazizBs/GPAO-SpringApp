package com.housservice.housstock.service;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PlanificationMappper;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlanificationServiceImpl implements PlanificationService {

    final SequenceGeneratorService sequenceGeneratorService;
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    final
    PersonnelRepository personnelRepository;

    final
    MachineRepository machineRepository;
    final
    EtapeProductionRepository etapeProductionRepository;

    final
    PlanificationRepository planificationRepository;
    final
    LigneCommandeClientRepository ligneCommandeClientRepository;

    public PlanificationServiceImpl(SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties, PersonnelRepository personnelRepository, MachineRepository machineRepository, EtapeProductionRepository etapeProductionRepository, LigneCommandeClientRepository ligneCommandeClientRepository, PlanificationRepository planificationRepository) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.personnelRepository = personnelRepository;
        this.machineRepository = machineRepository;
        this.etapeProductionRepository = etapeProductionRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.planificationRepository = planificationRepository;
    }


    @Override
    public void updatePlanfication( PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        PlanificationOf planificationOf = PlanificationMappper.MAPPER.toPlanificationOf(planificationOfDTO);
         List<Personnel> personnels= new ArrayList<>();
        if (!planificationOfDTO.getIdPersonnels().isEmpty()){
            planificationOfDTO.getIdPersonnels().forEach(idP ->
                   {
                       try {
                          personnels.add(personnelRepository.findById(idP)
                                   .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdPersonnels()))
                                      ));
                       } catch (ResourceNotFoundException e) {
                           throw new RuntimeException(e);
                       }
                   }
              );
        }
        if (!planificationOfDTO.getMachineId().isEmpty()){
            Machine machine = machineRepository.findById(planificationOfDTO.getMachineId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getMachineId())));
            planificationOf.setMachine(machine);
        }
                planificationOf.setPersonnels(personnels);
                planificationRepository.save(planificationOf);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getPlanificationByIdLigneCmdAndIndex(String idLC, int index) {
        try {
           List<PlanificationOf> planifications = planificationRepository.findAll();
            List<PlanificationOf> planificationsOf = planifications.stream().filter(planificationOf -> {
                return planificationOf.getLigneCommandeClient().getId().equals(idLC);
            }).collect(Collectors.toList());
            PlanificationOfDTO planificationOfDTO = PlanificationMappper.MAPPER.toPlanificationOfDto(planificationsOf.get(index));
            Map<String, Object> response = new HashMap<>();
            response.put("planification", planificationOfDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PlanificationOfDTO> getPlanificationByIdLigneCmd(String idLc) throws ResourceNotFoundException {
        List<PlanificationOf> planificationOfs = planificationRepository.findAll();
        planificationOfs = planificationOfs.stream().filter(planificationOf -> planificationOf.getLigneCommandeClient().getId().equals(idLc)).collect(Collectors.toList());
        return planificationOfs.stream().map(PlanificationMappper.MAPPER::toPlanificationOfDto).collect(Collectors.toList());
    }

    @Override
    public List<PlanificationOfDTO> getAllPlanificationsParOperation(String operationType) throws ResourceNotFoundException {
        List<PlanificationOf> planificationOfs = planificationRepository.findPlanificationOfByLanced(true);
            planificationOfs = planificationOfs.stream().filter(planificationOf -> planificationOf.getOperationType().equals(operationType)).collect(Collectors.toList());
            return planificationOfs.stream().map(PlanificationMappper.MAPPER::toPlanificationOfDto).collect(Collectors.toList());
    }

}
