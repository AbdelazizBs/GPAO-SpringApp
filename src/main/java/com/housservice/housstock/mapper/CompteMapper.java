package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.dto.CompteDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CompteMapper {

    public static CompteMapper MAPPER = Mappers.getMapper(CompteMapper.class);

    public abstract CompteDto toCompteDto(Compte compte);

    public abstract Compte toCompte(CompteDto  compteDto);
    @AfterMapping
    void updateCompteDto(final Compte  compte, @MappingTarget final CompteDto compteDto)   {

    }

    @AfterMapping
    void updateCompte(final CompteDto compteDto, @MappingTarget final Compte compte) {

    }


}
