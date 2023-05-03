package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.PlannificationDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PlannificationMapper {

    public static PlannificationMapper MAPPER = Mappers.getMapper(PlannificationMapper.class);

    public abstract PlannificationDto toPlannificationDto(Plannification plannification);

    public abstract Plannification toPlannification(PlannificationDto  plannificationDto);



    @AfterMapping
    void updatePlannificationDto(final Plannification plannification, @MappingTarget final PlannificationDto plannificationDto)   {
    }

    @AfterMapping
    void updatePlannification(final PlannificationDto  plannificationDto, @MappingTarget final Plannification plannification) {
    }
}