package com.housservice.housstock.mapper;


import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.ComptesDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompteMapper {


    public static CompteMapper  MAPPER = Mappers.getMapper(CompteMapper.class);




    public abstract ComptesDto toComptesDto(Comptes comptes);

    public abstract Comptes toComptes(ComptesDto  comptesDto);

    @AfterMapping
    void updateComptesDto(final Comptes comptes, @MappingTarget final ComptesDto comptesDto)   {
        comptesDto.setRolesName(comptes.getRoles().stream().map(Roles::getNom).collect(Collectors.toList()));
    }

    @AfterMapping
    void updateComptes(final ComptesDto  comptesDto, @MappingTarget final Comptes comptes) {
    }

}
