package com.housservice.housstock.mapper;

import com.housservice.housstock.model.TypePapier;
import com.housservice.housstock.model.dto.TypePapierDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public abstract class TypePapierMapper {
    public static TypePapierMapper MAPPER = Mappers.getMapper(TypePapierMapper.class);

    public abstract TypePapierDto toTypePapierDto(TypePapier typePapier);

    public abstract TypePapier toTypePapier(TypePapierDto typePapierDto);



    @AfterMapping
    void updateTypePapierDto(final TypePapier typePapier, @MappingTarget final TypePapierDto typePapierDto)   {

    }

    @AfterMapping
    void updateTypePapier(final TypePapierDto  typePapierDto, @MappingTarget final TypePapier typePapier) {

    }
}
