package com.housservice.housstock.mapper;

import com.housservice.housstock.model.UniteConsommation;
import com.housservice.housstock.model.dto.UniteConsommationDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class UniteConsommationMapper {
    public static UniteConsommationMapper MAPPER = Mappers.getMapper(UniteConsommationMapper.class);

    public abstract UniteConsommationDto toUniteConsommationDto(UniteConsommation uniteConsommation);

    public abstract UniteConsommation toUniteConsommation(UniteConsommationDto uniteConsommationDto);



    @AfterMapping
    void updateUniteConsommationDto(final UniteConsommation uniteConsommation, @MappingTarget final UniteConsommationDto uniteConsommationDto)   {

    }

    @AfterMapping
    void updateUniteConsommation(final UniteConsommationDto  uniteConsommationDto, @MappingTarget final UniteConsommation uniteConsommation) {

    }
}
