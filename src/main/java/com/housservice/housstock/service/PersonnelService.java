package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;

public interface PersonnelService {
	
	public List<PersonnelDto> getAllPersonnel();
	public List<String> getAllNomPersonnel();

    public PersonnelDto getPersonnelById(String id);
	
    public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel);

    public void createNewPersonnel(@Valid PersonnelDto personnelDto);
	
    public void updatePersonnel(@Valid PersonnelDto personnelDto) throws ResourceNotFoundException;
    
    public void deletePersonnel(String personnelId);

}
