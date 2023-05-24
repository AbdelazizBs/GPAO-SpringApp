package com.housservice.housstock.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.model.dto.MachineDto;
import org.springframework.http.ResponseEntity;

public interface ComptesService {
	
	public List<ComptesDto> getAllComptes();
		
    public ComptesDto getComptesById(String id);
	

//    public void addCompte(String idPersonnel ,String email, String password, List<String> roles) throws ResourceNotFoundException;
    void addCompte(String idPersonnel , ComptesDto comptesDto) throws ResourceNotFoundException;
    void miseEnVeille(String idCompte) throws ResourceNotFoundException;
    void restaurer(String idCompte) throws ResourceNotFoundException;
    void deleteCompte(String idCompte) throws ResourceNotFoundException;
     List<String> getRoles(String  email);
      ResponseEntity<Map<String, Object>> getAllCompte(int page, int size);
      ResponseEntity<Map<String, Object>> getAllCompteEnVeille(int page, int size);

     void updateCompte(String idPersonnel ,@Valid ComptesDto comptesDto) throws ResourceNotFoundException;
    

}
