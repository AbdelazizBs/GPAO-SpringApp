package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public abstract class PlanificationMappper   {

    public static PlanificationMappper  MAPPER = Mappers.getMapper(PlanificationMappper.class);

    public abstract PlanificationOf toPlanificationOf(PlanificationOfDTO planificationOfDTO);

    public abstract PlanificationOfDTO toPlanificationOfDto(PlanificationOf planificationOf);

    @AfterMapping
    void updatePlanificationDto(final PlanificationOf planificationOf, @MappingTarget final PlanificationOfDTO planificationOfDTO)   {
    planificationOfDTO.setNomPersonnels(planificationOf.getPersonnels().size()>0 ? planificationOf.getPersonnels().stream().map(Personnel::getNom).collect(Collectors.toList()):new ArrayList<>());
            planificationOfDTO.setIdPersonnels(planificationOf.getPersonnels().size()>0 ? planificationOf.getPersonnels().stream().map(Personnel::getId).collect(Collectors.toList()):new ArrayList<>());
    planificationOfDTO.setMachineId(planificationOf.getMachine() !=null ? planificationOf.getMachine().getId():"");
    planificationOfDTO.setLibelleMachine( planificationOf.getMachine() !=null ? planificationOf.getMachine().getLibelle():"");
    }

    @AfterMapping
    void updatePlanification(final PlanificationOfDTO   planificationOfDTO, @MappingTarget final PlanificationOf planificationOf) {

    }

}
