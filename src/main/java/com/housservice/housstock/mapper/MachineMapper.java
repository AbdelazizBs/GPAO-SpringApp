package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.MachineDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public abstract class MachineMapper {

    public static MachineMapper MAPPER = Mappers.getMapper(MachineMapper.class);


    public abstract MachineDto toMachineDto(Machine machine);

    public abstract Machine toMachine(MachineDto machineDto);

    @AfterMapping
    void updateMachineDto(final Machine machine, @MappingTarget final MachineDto machineDto) {
        List<String> nomConducteurs = new ArrayList<>();
        for (Personnel personnel : machine.getPersonnel()) {
            nomConducteurs.add(personnel.getNom());
        }
        machineDto.setNomEtapeProduction(machine.getEtapeProduction().getNomEtape());
        // set etat machine name when mapping and check if
        machineDto.setNomEtatMachine(machine.getEtatMachine().getLastEtat());
        machineDto.setNomConducteurs(nomConducteurs);
    }

    @AfterMapping
    void updateMachine(final MachineDto machineDto, @MappingTarget final Machine machine) {
    }


}
