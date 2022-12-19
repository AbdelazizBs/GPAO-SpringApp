package com.housservice.housstock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;

@Mapper(componentModel = "spring")
public abstract class FournisseurMapper {
	
	  public static FournisseurMapper  MAPPER = Mappers.getMapper(FournisseurMapper.class);

	  public abstract FournisseurDto toFournisseurDto(Fournisseur fournisseur);

	  public abstract Fournisseur toFournisseur(FournisseurDto  fournisseurDto);

}
