package com.housservice.housstock.mapper;

import com.housservice.housstock.model.TypeMachine;
import com.housservice.housstock.model.dto.TypeMachineDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public abstract class TypeMachineMapper {
    public static TypeMachineMapper MAPPER = Mappers.getMapper(TypeMachineMapper.class);

    public abstract TypeMachineDto toTypeMachineDto(TypeMachine typeMachine);

    public abstract TypeMachine toTypeMachine(TypeMachineDto typeMachineDto);



    @AfterMapping
    void updateTypeMachineDto(final TypeMachine typeMachine, @MappingTarget final TypeMachineDto typeMachineDto)   {

    }

    @AfterMapping
    void updateTypeMachine(final TypeMachineDto  typeMachineDto, @MappingTarget final TypeMachine typeMachine) {

    }

}
