package com.housservice.housstock.mapper;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class PersonnelMapper {

    public static PersonnelMapper  MAPPER = Mappers.getMapper(PersonnelMapper.class);


    public abstract PersonnelDto toPersonnelDto(Personnel personnel);

    public abstract Personnel toPersonnel(PersonnelDto  personnelDto);



    @AfterMapping
    void updatePersonnelDto(final Personnel personnel, @MappingTarget final PersonnelDto personnelDto)   {

    }

    @AfterMapping
    void updatePersonnel(final PersonnelDto  personnelDto, @MappingTarget final Personnel personnel) {

    }

}

