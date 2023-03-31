package com.housservice.housstock.mapper;


import com.housservice.housstock.model.CommandeSuivi;
import com.housservice.housstock.model.dto.CommandeSuiviDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommandeSuiviMapper {

    public static CommandeSuiviMapper  MAPPER = Mappers.getMapper(CommandeSuiviMapper.class);

    public abstract CommandeSuiviDto toCommandeSuiviDto(CommandeSuivi commandeSuivi);

    public abstract CommandeSuivi toCommandeSuivi(CommandeSuiviDto  commandeSuiviDto);



    @AfterMapping
    void updateCommandeSuiviDto(final CommandeSuivi commandeSuivi, @MappingTarget final CommandeSuiviDto commandeSuiviDto)   {
    }

    @AfterMapping
    void updateCommandeSuivi(final CommandeSuiviDto  commandeSuiviDto, @MappingTarget final CommandeSuivi commandeSuivi) {
    }
}