package com.housservice.housstock.service;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.repository.*;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
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
    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        planificationRepository.save(buildPlanificationOfFromPlanificationOfDTO(planificationOfDTO));


    }

    @Override
    public PlanificationOfDTO buildPlanificationOfDTOFromPlanificationOf(PlanificationOf planificationOf) {
        if (planificationOf == null) {
            return null;
        }

        PlanificationOfDTO planificationOfDTO = new PlanificationOfDTO();
        planificationOfDTO.setId(planificationOf.getId());
        List<String> idPersonnels = planificationOf.getPersonnels().stream().map(personnel -> personnel.getId()).collect(Collectors.toList());
        planificationOfDTO.setIdPersonnels(idPersonnels);
        planificationOfDTO.setDateLancementReel(planificationOf.getDateLancementReel());
        planificationOfDTO.setHeureDebutReel(planificationOf.getHeureDebutReel());
        planificationOfDTO.setHeureFinReel(planificationOf.getHeureFinReel());
        planificationOfDTO.setQuantiteInitiale(planificationOf.getQuantiteInitiale());
        planificationOfDTO.setIdLigneCommandeClient(planificationOf.getLigneCommandeClient().getId());
        planificationOfDTO.setNomEtape(planificationOf.getNomEtape());
        planificationOfDTO.setRefMachine(planificationOf.getMachine().getReference());
        return planificationOfDTO;

    }

    @Override
    public PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        PlanificationOf planificationOf = new PlanificationOf();

        planificationOf.setId("" + sequenceGeneratorService.generateSequence(PlanificationOf.SEQUENCE_NAME));
        planificationOf.setId(planificationOfDTO.getId());
        planificationOf.setDateLancementReel(planificationOfDTO.getDateLancementReel());
        planificationOf.setHeureDebutReel(planificationOfDTO.getHeureDebutReel());
        planificationOf.setHeureFinReel(planificationOfDTO.getHeureFinReel());
        planificationOf.setQuantiteInitiale(planificationOfDTO.getQuantiteInitiale());
        List<Personnel> personnels = planificationOfDTO.getIdPersonnels().stream().map(s -> {
            try {
                return personnelRepository.findById(s)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), s)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        planificationOf.setPersonnels(personnels);
        planificationOf.setMachine(machineRepository.findMachineByReference(planificationOfDTO.getRefMachine())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getRefMachine()))));
        planificationOf.setNomEtape(planificationOfDTO.getNomEtape());
        planificationOf.setLigneCommandeClient(ligneCommandeClientRepository.findById(planificationOfDTO.getIdLigneCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdLigneCommandeClient()))));

        return planificationOf;
    }

    @Override
    public List<PlanificationOf> getPlanificationEtape(String idLc) throws ResourceNotFoundException {
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(idLc)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idLc)));
        return planificationRepository.findPlanificationOfByLigneCommandeClient(ligneCommandeClient);
    }

    @Override
    public PlanificationOf getPlanificationByIdLigneCmdAndNamEtape(String idLc, String nomEtape) throws ResourceNotFoundException {
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(idLc)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idLc)));
        List<PlanificationOf> planificationOf = planificationRepository.findPlanificationOfByLigneCommandeClient(ligneCommandeClient);
        return planificationOf.stream().filter(planificationOf1 -> planificationOf1.getNomEtape().equals(nomEtape)).findFirst().orElse(null);
    }

}
