package com.housservice.housstock.mapper;


import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommandMapper {

    public static CommandMapper  MAPPER = Mappers.getMapper(CommandMapper.class);



    public abstract CommandeClientDto toCommandDto(CommandeClient commandeClient);

    public abstract CommandeClient toCommand(CommandeClientDto  commandeClientDto);

    @AfterMapping
    void updateCommandDto(final CommandeClient commandeClient, @MappingTarget final CommandeClientDto commandeClientDto)   {

    }

    @AfterMapping
    void updateCommand(final CommandeClientDto  commandeClientDto, @MappingTarget final CommandeClient commandeClient) {

    }

}
