package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public abstract class PlanificationMappper   {

    public static PlanificationMappper  MAPPER = Mappers.getMapper(PlanificationMappper.class);

    public abstract PlanificationOf toPlanificationOf(PlanificationOfDTO planificationOfDTO);

    public abstract PlanificationOfDTO toPlanificationOfDto(PlanificationOf planificationOf);

    @AfterMapping
    void updatePlanificationDto(final PlanificationOf planificationOf, @MappingTarget final PlanificationOfDTO planificationOfDTO)   {
    planificationOfDTO.setNomPersonnels(planificationOf.getPersonnels().stream().map(Personnel::getNom).collect(Collectors.toList()));
    }

    @AfterMapping
    void updatePlanification(final PlanificationOfDTO   planificationOfDTO, @MappingTarget final PlanificationOf planificationOf) {

    }

}
