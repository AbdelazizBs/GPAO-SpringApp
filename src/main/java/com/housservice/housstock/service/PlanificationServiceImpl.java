package com.housservice.housstock.service;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.repository.*;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanificationServiceImpl implements  PlanificationService{

    final SequenceGeneratorService sequenceGeneratorService;
    private final MessageHttpErrorProperties messageHttpErrorProperties;
final
PersonnelRepository personnelRepository;

final
MachineRepository machineRepository ;
final
EtapeProductionRepository etapeProductionRepository ;

    final
    PlanificationRepository planificationRepository ;
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
    public List<PlanificationOfDTO> getPlanificationMachineByIdLc(String id) {
         return planificationRepository.findByLigneCommandeClientIdAndEtapeProductionsTypeEtape(id,"Machine").stream().map(planificationOf ->
             buildPlanificationOfDTOFromPlanificationOf(planificationOf)).collect(Collectors.toList());
    }
    @Override
    public List<PlanificationOfDTO> getPlanificationManuelleByIdLc(String id) {
         return planificationRepository.findByLigneCommandeClientIdAndEtapeProductionsTypeEtape(id,"Manuelle").stream().map(planificationOf ->
             buildPlanificationOfDTOFromPlanificationOf(planificationOf)).collect(Collectors.toList());
    }
    @Override
    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        planificationRepository.save(buildPlanificationOfFromPlanificationOfDTO(planificationOfDTO));


    }

    @Override
    public PlanificationOfDTO buildPlanificationOfDTOFromPlanificationOf(PlanificationOf planificationOf) {
        if (planificationOf == null)
        {
            return null;
        }

        PlanificationOfDTO planificationOfDTO = new PlanificationOfDTO();
        planificationOfDTO.setId(planificationOf.getId());
        planificationOfDTO.setCommentaire(planificationOf.getCommentaire());
        List<String> idPersonnels = planificationOf.getPersonnels().stream().map(personnel -> personnel.getId()).collect(Collectors.toList());
        planificationOfDTO.setIdPersonnels(idPersonnels);
        planificationOfDTO.setDuréeReelOperation(planificationOf.getDuréeReelOperation());
        planificationOfDTO.setDateLancementReel(planificationOf.getDateLancementReel());
        planificationOfDTO.setDateLancementPrevue(planificationOf.getDateLancementPrevue());
        planificationOfDTO.setHeureDebutPrevue(planificationOf.getHeureDebutPrevue());
        planificationOfDTO.setHeureDebutReel(planificationOf.getHeureDebutReel());
        planificationOfDTO.setHeureFinReel(planificationOf.getHeureFinReel());
        planificationOfDTO.setQuantiteInitiale(planificationOf.getQuantiteInitiale());
        planificationOfDTO.setQuantiteConforme(planificationOf.getQuantiteConforme());
        planificationOfDTO.setQuantiteNonConforme(planificationOf.getQuantiteNonConforme());
        planificationOfDTO.setHeureFinPrevue(planificationOf.getHeureFinPrevue());
        planificationOfDTO.setIdLigneCommandeClient(planificationOf.getLigneCommandeClient().getId());
        planificationOfDTO.setNomEtape(planificationOf.getEtapeProductions().getNomEtape());
        planificationOfDTO.setIdMachine(planificationOf.getMachine().getId());
        return planificationOfDTO;

    }

    @Override
    public PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        PlanificationOf planificationOf = new PlanificationOf();

        planificationOf.setId(""+sequenceGeneratorService.generateSequence(PlanificationOf.SEQUENCE_NAME));
        planificationOf.setId(planificationOfDTO.getId());
        planificationOf.setCommentaire(planificationOfDTO.getCommentaire());
        planificationOf.setDateLancementPrevue(planificationOfDTO.getDateLancementPrevue());
        planificationOf.setDateLancementReel(planificationOfDTO.getDateLancementReel());
        planificationOf.setHeureDebutPrevue(planificationOfDTO.getHeureDebutPrevue());
        planificationOf.setHeureDebutReel(planificationOfDTO.getHeureDebutReel());
        planificationOf.setDuréeReelOperation(planificationOfDTO.getDuréeReelOperation());
        planificationOf.setHeureFinPrevue(planificationOfDTO.getHeureFinPrevue());
        planificationOf.setHeureFinReel(planificationOfDTO.getHeureFinReel());
        planificationOf.setQuantiteConforme(planificationOfDTO.getQuantiteConforme());
        planificationOf.setQuantiteNonConforme(planificationOfDTO.getQuantiteNonConforme());
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
        planificationOf.setMachine(machineRepository.findById(planificationOfDTO.getIdMachine())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdMachine()))));
        planificationOf.setEtapeProductions(etapeProductionRepository.findByNomEtape(planificationOfDTO.getNomEtape())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getNomEtape()))));
        planificationOf.setLigneCommandeClient(ligneCommandeClientRepository.findById(planificationOfDTO.getIdLigneCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdLigneCommandeClient()))));

        return planificationOf;
    }



}
