package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.CommandeDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommandeClientMapper {
    public static CommandeClientMapper   MAPPER = Mappers.getMapper(CommandeClientMapper .class);

    public abstract CommandeClientDto toCommandeClientDto(CommandeClient commandeClient);

    public abstract CommandeClient toCommandeClient(CommandeClientDto  commandeClientDto);
    @AfterMapping
    void updateClientDto(final CommandeClient commandeClient, @MappingTarget final CommandeClientDto commandeClientDto)   {

    }

    @AfterMapping
    void updateClient(final CommandeClientDto commandeClientDto, @MappingTarget final CommandeClient commandeClient) {

    }
}
