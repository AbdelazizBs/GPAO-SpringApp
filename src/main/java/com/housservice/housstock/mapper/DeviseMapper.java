package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Devise;
import com.housservice.housstock.model.dto.DeviseDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class DeviseMapper {
    public static DeviseMapper MAPPER = Mappers.getMapper(DeviseMapper.class);

    public abstract DeviseDto toDeviseDto(Devise devise);

    public abstract Devise toDevise(DeviseDto deviseDto);



    @AfterMapping
    void updateDeviseDto(final Devise devise, @MappingTarget final DeviseDto deviseDto)   {

    }

    @AfterMapping
    void updateDevise(final DeviseDto  deviseDto, @MappingTarget final Devise devise) {

    }
}
