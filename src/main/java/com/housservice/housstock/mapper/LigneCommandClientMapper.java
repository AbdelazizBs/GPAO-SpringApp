package com.housservice.housstock.mapper;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class LigneCommandClientMapper {

    public static LigneCommandClientMapper  MAPPER = Mappers.getMapper(LigneCommandClientMapper.class);



    public abstract LigneCommandeClientDto toLigneCommandClientDto(LigneCommandeClient ligneCommandClient);

    public abstract LigneCommandeClient toLigneCommandClient(LigneCommandeClientDto ligneCommandeClientDto);

    @AfterMapping
    void updateLigneCommandClientDto(final LigneCommandeClient ligneCommandClient, @MappingTarget final LigneCommandeClientDto ligneCommandeClientDto)  {
    }

    @AfterMapping
    void updateLigneCommandClient(final LigneCommandeClientDto ligneCommandeClientDto, @MappingTarget final LigneCommandeClient ligneCommandClient) {

    }

}
