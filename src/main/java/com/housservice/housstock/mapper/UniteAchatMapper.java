package com.housservice.housstock.mapper;

import com.housservice.housstock.model.UniteAchat;
import com.housservice.housstock.model.dto.UniteAchatDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class UniteAchatMapper {
    public static UniteAchatMapper MAPPER = Mappers.getMapper(UniteAchatMapper.class);

    public abstract UniteAchatDto toUniteAchatDto(UniteAchat uniteAchat);

    public abstract UniteAchat toUniteAchat(UniteAchatDto uniteAchatDto);



    @AfterMapping
    void updateUniteAchatDto(final UniteAchat uniteAchat, @MappingTarget final UniteAchatDto uniteAchatDto)   {

    }

    @AfterMapping
    void updateUniteAchat(final UniteAchatDto  uniteAchatDto, @MappingTarget final UniteAchat uniteAchat) {

    }
}
