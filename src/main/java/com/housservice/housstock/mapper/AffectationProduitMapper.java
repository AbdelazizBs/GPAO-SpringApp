package com.housservice.housstock.mapper;


import com.housservice.housstock.model.AffectationProduit;
import com.housservice.housstock.model.dto.AffectationProduitDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AffectationProduitMapper {

    public static AffectationProduitMapper  MAPPER = Mappers.getMapper(AffectationProduitMapper.class);

    public abstract AffectationProduitDto toAffectationProduitDto(AffectationProduit affectationProduit);

    public abstract AffectationProduit toAffectationProduit(AffectationProduitDto  affectationProduitDto);



    @AfterMapping
    void updateAffectationProduitDto(final AffectationProduit affectationProduit, @MappingTarget final AffectationProduitDto affectationProduitDto)   {
    }

    @AfterMapping
    void updateAffectationProduit(final AffectationProduitDto  affectationProduitDto, @MappingTarget final AffectationProduit affectationProduit) {
    }
}