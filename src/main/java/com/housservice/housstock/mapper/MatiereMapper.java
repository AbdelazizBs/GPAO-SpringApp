package com.housservice.housstock.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;

@Mapper(componentModel = "spring")
public abstract class MatiereMapper {
	
	 public static MatiereMapper  MAPPER = Mappers.getMapper(MatiereMapper.class);


	    public abstract MatiereDto toMatiereDto(Matiere matiere);

	    public abstract Matiere toMatiere(MatiereDto  matiereDto);



	    @AfterMapping
	    void updateMatiereDto(final Matiere matiere, @MappingTarget final MatiereDto matiereDto)   {

	    }

	    @AfterMapping
	    void updateMatiere(final MatiereDto  matiereDto, @MappingTarget final Matiere matiere) {

	    }

}
