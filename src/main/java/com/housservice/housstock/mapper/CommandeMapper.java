package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.dto.CommandeDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommandeMapper {
    public static CommandeMapper   MAPPER = Mappers.getMapper(CommandeMapper .class);

    public abstract CommandeDto toCommandeDto(Commande commande);

    public abstract Commande toCommande(CommandeDto  commandeDto);
    @AfterMapping
    void updateFournisseurDto(final Commande commande, @MappingTarget final CommandeDto commandeDto)   {

    }

    @AfterMapping
    void updateFournisseur(final CommandeDto commandeDto, @MappingTarget final Commande commande) {

    }
}