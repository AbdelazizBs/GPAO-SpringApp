package com.housservice.housstock.mapper;

import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public abstract class PlanEtapesMapper {
    public static PlanEtapesMapper MAPPER = Mappers.getMapper(PlanEtapesMapper.class);
    public abstract PlanEtapesDto toPlanEtapesDto(PlanEtapes planEtapes);

    public abstract PlanEtapes toPlanEtapes(PlanEtapesDto planEtapesDto);



    @AfterMapping
    void updatePlanEtapesDto(final PlanEtapes planEtapes, @MappingTarget final PlanEtapesDto planEtapesDto)   {

    }

    @AfterMapping
    void updatePlanEtapes(final PlanEtapesDto  planEtapesDto, @MappingTarget final PlanEtapes planEtapes) {

    }

}
