package com.housservice.housstock.mapper;


import com.housservice.housstock.model.PrixAchat;
import com.housservice.housstock.model.dto.PrixAchatDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PrixAchatMapper {

    public static PrixAchatMapper  MAPPER = Mappers.getMapper(PrixAchatMapper.class);

    public abstract PrixAchatDto toPrixAchatDto(PrixAchat prixAchat);

    public abstract PrixAchat toPrixAchat(PrixAchatDto  prixAchatDto);



    @AfterMapping
    void updatePrixAchatDto(final PrixAchat prixAchat, @MappingTarget final PrixAchatDto prixAchatDto)   {
    }

    @AfterMapping
    void updatePrixAchat(final PrixAchatDto  prixAchatDto, @MappingTarget final PrixAchat prixAchat) {
    }
}