package com.housservice.housstock.mapper;

import com.housservice.housstock.model.MatierePrimaire;
import com.housservice.housstock.model.dto.MatierePrimaireDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class MatierePrimaireMapper {
    public static MatierePrimaireMapper MAPPER = Mappers.getMapper(MatierePrimaireMapper.class);

    public abstract MatierePrimaireDto toMatiereDto(MatierePrimaire matiere);

    public abstract MatierePrimaire toMatierePrimaire(MatierePrimaireDto matiereDto);



    @AfterMapping
    void updateMatierePrimaireDto(final MatierePrimaire matiere, @MappingTarget final MatierePrimaireDto matierePrimaireDto)   {

    }

    @AfterMapping
    void updateMatiere(final MatierePrimaireDto  matiereDto, @MappingTarget final MatierePrimaire matierePrimaire) {

    }


}
