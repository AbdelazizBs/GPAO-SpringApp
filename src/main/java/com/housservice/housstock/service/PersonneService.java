package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personne;
import com.housservice.housstock.model.dto.PersonneDto;

public interface PersonneService {

	public List<PersonneDto> getAllPersonne();
	
    public PersonneDto getPersonneById(String id);
	
    public PersonneDto buildPersonneDtoFromPersonne(Personne personne);

    public void createNewPersonne(@Valid PersonneDto personneDto);
	
    public void updatePersonne(@Valid PersonneDto personneDto) throws ResourceNotFoundException;
    
    public void deletePersonne(String personneId);
}
