package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    public static ClientMapper  MAPPER = Mappers.getMapper(ClientMapper.class);

    public abstract ClientDto toClientDto(Client client);

    public abstract Client toClient(ClientDto  clientDto);
    @AfterMapping
    void updateClientDto(final Client  client, @MappingTarget final ClientDto clientDto)   {

    }

    @AfterMapping
    void updateClient(final ClientDto clientDto, @MappingTarget final Client client) {

    }


}
