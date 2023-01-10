package com.housservice.housstock.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;

@Mapper(componentModel = "spring")
public abstract class NomenclatureMapper {
  
	  public static NomenclatureMapper  MAPPER = Mappers.getMapper(NomenclatureMapper.class);
	  
	  public abstract NomenclatureDto toNomenclatureDto(Nomenclature nomenclature);

	  public abstract Nomenclature toNomenclature(NomenclatureDto  nomenclatureDto);


	    @AfterMapping
	    void updateNomenclatureDto(final Nomenclature Nomenclature, @MappingTarget final NomenclatureDto NomenclatureDto)   {

	    }

	    @AfterMapping
	    void updateNomenclature(final NomenclatureDto  NomenclatureDto, @MappingTarget final Nomenclature Nomenclature) {

	    }

	
}
