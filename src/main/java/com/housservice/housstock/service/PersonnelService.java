package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface PersonnelService {
	
	public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size);
    public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size);


    public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException;
    public Personnel getPersonnelByEmail(String email) throws ResourceNotFoundException;
    public Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException;


    // add new personnelDto
    void addPersonnel(PersonnelDto personnelDto) ;
    // update personnelDto

    void updatePersonnel(PersonnelDto personnelDto,String idPersonnel) throws ResourceNotFoundException;


    void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size,boolean enVeille);

    void deletePersonnel(String personnelId);
    void deletePersonnelSelected(List<String> idPersonnelsSelected);

}
