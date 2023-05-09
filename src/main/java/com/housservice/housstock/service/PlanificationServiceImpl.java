package com.housservice.housstock.service;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PlanificationMappper;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
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
        List<Personnel> personnels = planificationOfDTO.getIdPersonnels().stream().map(s -> {
            try {
                return personnelRepository.findById(s)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), s)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
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
            PlanificationOf planificationOf = planificationsOf.get(index);
            Map<String, Object> response = new HashMap<>();
            response.put("planification", planificationOf);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PlanificationOf> getPlanificationByIdLigneCmd(String idLc) throws ResourceNotFoundException {
        List<PlanificationOf> planificationOfs = planificationRepository.findAll();
        planificationOfs = planificationOfs.stream().filter(planificationOf -> planificationOf.getLigneCommandeClient().getId().equals(idLc)).collect(Collectors.toList());
        return planificationOfs;
    }

}
