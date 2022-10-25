package com.housservice.housstock.mapper;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.ComptesDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper
public abstract class CompteMapper {

    public static CompteMapper MAPPER = Mappers.getMapper(CompteMapper.class);


    @Mapping(target = "roles", ignore = true)
    public abstract ComptesDto toComptesDto(Comptes comptes);

    @Mapping(target = "roles", ignore = true)
    public abstract Comptes toComptes(ComptesDto comptesDto);



    @AfterMapping
    void updateComptesDto(final Comptes comptes, @MappingTarget final ComptesDto comptesDto) throws ResourceNotFoundException {
        final List<String> list =
                comptes.getRoles().stream().map(Roles::getNom).collect(toList());
        comptesDto.setRoles(list);
    }

    @AfterMapping
    void updateComptes(final ComptesDto  comptesDto, @MappingTarget final Comptes  comptes) {

    }

}
