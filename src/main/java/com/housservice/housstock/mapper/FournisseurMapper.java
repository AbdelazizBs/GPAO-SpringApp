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

	    public abstract FournisseurDto toFournisseurDto(Fournisseur Fournisseur);

	    public abstract Fournisseur toFournisseur(FournisseurDto  FournisseurDto);


	    @AfterMapping
	    void updateFournisseurDto(final Fournisseur Fournisseur, @MappingTarget final FournisseurDto FournisseurDto)   {

	    }

	    @AfterMapping
	    void updateFournisseur(final FournisseurDto  FournisseurDto, @MappingTarget final Fournisseur Fournisseur) {

	    }
	  	    
}
