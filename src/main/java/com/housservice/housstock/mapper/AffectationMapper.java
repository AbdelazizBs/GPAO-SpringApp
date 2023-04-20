package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.dto.AffectationDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AffectationMapper {

    public static AffectationMapper  MAPPER = Mappers.getMapper(AffectationMapper.class);

    public abstract AffectationDto toAffectationDto(Affectation affectation);

    public abstract Affectation toAffectation(AffectationDto  affectationDto);



    @AfterMapping
    void updateAffectationDto(final Affectation affectation, @MappingTarget final AffectationDto affectationDto)   {
    }

    @AfterMapping
    void updateAffectation(final AffectationDto  affectationDto, @MappingTarget final Affectation affectation) {
    }
}