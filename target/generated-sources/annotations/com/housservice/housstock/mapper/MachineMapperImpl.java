package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-20T04:06:16+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class MachineMapperImpl extends MachineMapper {

    @Override
    public MachineDto toMachineDto(Machine machine) {
        if ( machine == null ) {
            return null;
        }

        MachineDto machineDto = new MachineDto();

        machineDto.setId( machine.getId() );
        machineDto.setReference( machine.getReference() );
        machineDto.setLibelle( machine.getLibelle() );
        machineDto.setNbrConducteur( machine.getNbrConducteur() );
        machineDto.setDateMaintenance( machine.getDateMaintenance() );
        machineDto.setEnVeille( machine.getEnVeille() );

        updateMachineDto( machine, machineDto );

        return machineDto;
    }

    @Override
    public Machine toMachine(MachineDto machineDto) {
        if ( machineDto == null ) {
            return null;
        }

        Machine machine = new Machine();

        machine.setId( machineDto.getId() );
        machine.setReference( machineDto.getReference() );
        machine.setLibelle( machineDto.getLibelle() );
        machine.setNbrConducteur( machineDto.getNbrConducteur() );
        machine.setDateMaintenance( machineDto.getDateMaintenance() );
        machine.setEnVeille( machineDto.getEnVeille() );

        updateMachine( machineDto, machine );

        return machine;
    }
}
