package com.housservice.housstock.mapper;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.repository.EtapeProductionRepository;
import com.housservice.housstock.repository.LigneCommandeClientRepository;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class PlanificationOfMapper {
    private final PersonnelRepository personnelRepository;
    private final LigneCommandeClientRepository  ligneCommandeClientRepository;
    private final MachineRepository machineRepository;
    private final EtapeProductionRepository etapeProductionRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public static PlanificationOfMapper MAPPER = Mappers.getMapper(PlanificationOfMapper.class);

    protected PlanificationOfMapper(PersonnelRepository personnelRepository, LigneCommandeClientRepository ligneCommandeClientRepository, MachineRepository machineRepository, EtapeProductionRepository etapeProductionRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.personnelRepository = personnelRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.machineRepository = machineRepository;
        this.etapeProductionRepository = etapeProductionRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


        @Mapping(target = "ligneCommandeClient", ignore = true)
        @Mapping(target = "etapeProductions", ignore = true)
        @Mapping(target = "personnels", ignore = true)
        @Mapping(target = "machine", ignore = true)
    public abstract PlanificationOf toPlanificationOf(PlanificationOfDTO planificationOfDTO);

    @Mapping(target = "idLigneCommandeClient", ignore = true)
    @Mapping(target = "idEtapeProductions", ignore = true)
    @Mapping(target = "idPersonnels", ignore = true)
    @Mapping(target = "idMachine", ignore = true)
    public abstract PlanificationOfDTO toPlanificationOfDTO(PlanificationOf planificationOf);

    @AfterMapping
    void updatePlanificationOfDTO(final PlanificationOf planificationOf, @MappingTarget final PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
//        planificationOf.setId(""+sequenceGeneratorService.generateSequence(PlanificationOf.SEQUENCE_NAME));
//        planificationOf.setId(planificationOfDTO.getId());
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
        planificationOf.setEtapeProductions(etapeProductionRepository.findById(planificationOfDTO.getIdEtapeProductions())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdEtapeProductions()))));
        planificationOf.setLigneCommandeClient(ligneCommandeClientRepository.findById(planificationOfDTO.getIdLigneCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), planificationOfDTO.getIdLigneCommandeClient()))));


    }

    @AfterMapping
    void updatePlanificationOf(final PlanificationOfDTO planificationOfDTO, @MappingTarget final PlanificationOf  planificationOf) {
//        PlanificationOfDTO planificationOfDTO = new PlanificationOfDTO();
//        planificationOfDTO.setId(planificationOf.getId());
        List<String> idPersonnels = planificationOf.getPersonnels().stream().map(personnel -> personnel.getId()).collect(Collectors.toList());
        planificationOfDTO.setIdPersonnels(idPersonnels);
        planificationOfDTO.setIdLigneCommandeClient(planificationOf.getLigneCommandeClient().getId());
        planificationOfDTO.setIdEtapeProductions(planificationOf.getEtapeProductions().getId());
        planificationOfDTO.setIdMachine(planificationOf.getMachine().getId());
    }


}