package com.housservice.housstock.mapper;

import com.housservice.housstock.model.CommandeClientSuivi;
import com.housservice.housstock.model.dto.CommandeClientSuiviDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class CommandeClientSuiviMapper {
    public static CommandeClientSuiviMapper  MAPPER = Mappers.getMapper(CommandeClientSuiviMapper.class);

    public abstract CommandeClientSuiviDto toCommandeClientSuiviDto(CommandeClientSuivi commandeClientSuivi);

    public abstract CommandeClientSuivi toCommandeClientSuivi(CommandeClientSuiviDto  commandeClientSuiviDto);



    @AfterMapping
    void updateCommandeClientSuiviDto(final CommandeClientSuivi commandeClientSuivi, @MappingTarget final CommandeClientSuiviDto commandeClientSuiviDto)   {
    }

    @AfterMapping
    void updateCommandeClientSuivi(final CommandeClientSuiviDto  commandeClientSuiviDto, @MappingTarget final CommandeClientSuivi commandeClientSuivi) {
    }
}
