package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.model.dto.MachineDto;

public interface ComptesService {
	
	public List<ComptesDto> getAllComptes();
		
    public ComptesDto getComptesById(String id);
	

//    public void addCompte(String idPersonnel ,String email, String password, List<String> roles) throws ResourceNotFoundException;
    void addCompte(String idPersonnel , ComptesDto comptesDto) throws ResourceNotFoundException;
     List<String> getRoles(String  email);
     List<ComptesDto> getAllCompte();

     void updateComptes(@Valid ComptesDto comptesDto) throws ResourceNotFoundException;
    
     void deleteComptes(String comptesId);

}
