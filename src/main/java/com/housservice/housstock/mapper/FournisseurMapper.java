package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class FournisseurMapper {

    public static FournisseurMapper  MAPPER = Mappers.getMapper(FournisseurMapper.class);

    public abstract FournisseurDto toFournisseurDto(Fournisseur fournisseur);

    public abstract Fournisseur toFournisseur(FournisseurDto  fournisseurDto);
    @AfterMapping
    void updateFournisseurDto(final Fournisseur  fournisseur, @MappingTarget final FournisseurDto fournisseurDto)   {

    }

    @AfterMapping
    void updateFournisseur(final FournisseurDto fournisseurDto, @MappingTarget final Fournisseur fournisseur) {

    }


}
