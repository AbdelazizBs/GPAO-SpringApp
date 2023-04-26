package com.housservice.housstock.mapper;


import com.housservice.housstock.model.PrixVente;
import com.housservice.housstock.model.dto.PrixVenteDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PrixVenteMapper {

    public static PrixVenteMapper  MAPPER = Mappers.getMapper(PrixVenteMapper.class);

    public abstract PrixVenteDto toPrixVenteDto(PrixVente prixVente);

    public abstract PrixVente toPrixVente(PrixVenteDto  prixVenteDto);



    @AfterMapping
    void updatePrixVenteDto(final PrixVente prixVente, @MappingTarget final PrixVenteDto prixVenteDto)   {
    }

    @AfterMapping
    void updatePrixVente(final PrixVenteDto  prixVenteDto, @MappingTarget final PrixVente prixVente) {
    }
}