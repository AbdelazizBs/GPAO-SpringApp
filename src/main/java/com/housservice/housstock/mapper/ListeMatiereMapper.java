package com.housservice.housstock.mapper;

import com.housservice.housstock.model.ListeMatiere;

import com.housservice.housstock.model.dto.ListeMatiereDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class ListeMatiereMapper {

    public static ListeMatiereMapper  MAPPER = Mappers.getMapper(ListeMatiereMapper.class);

    public abstract ListeMatiereDto toListeMatiereDto(ListeMatiere listeMatiere);

    public abstract ListeMatiere toListeMatiere(ListeMatiereDto  listeMatiereDto);
    @AfterMapping
    void updateListeMatiereDto(final ListeMatiere  listeMatiere, @MappingTarget final ListeMatiereDto listeMatiereDto)   {

    }

    @AfterMapping
    void updateListeMatiere(final ListeMatiereDto listeMatiereDto, @MappingTarget final ListeMatiere listeMatiere) {

    }
}
