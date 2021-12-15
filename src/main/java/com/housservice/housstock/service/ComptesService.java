package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.ComptesDto;

public interface ComptesService {
	
	public List<ComptesDto> getAllComptes();
		
    public ComptesDto getComptesById(String id);
	
    public ComptesDto buildComptesDtoFromComptes(Comptes comptes);
	
    public void createNewComptes(@Valid ComptesDto comptesDto);
	
    public void updateComptes(@Valid ComptesDto comptesDto) throws ResourceNotFoundException;
    
    public void deleteComptes(String comptesId);

}
