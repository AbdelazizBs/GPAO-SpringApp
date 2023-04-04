package com.housservice.housstock.service;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface PersonnelService {
	
	 ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size);
	 ResponseEntity<Map<String, Object>> onSortActivePersonnel(int page, int size, String field, String order);
	 ResponseEntity<Map<String, Object>> onSortPersonnelNotActive(int page, int size, String field, String order);
     ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size);
    public void Restaurer(String id) throws ResourceNotFoundException;

     PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException;
     Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException;


    // add new personnelDto
    void addPersonnel(PersonnelDto personnelDto) ;
    // update personnelDto

    void updatePersonnel(PersonnelDto personnelDto,String idPersonnel) throws ResourceNotFoundException;


    void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException;

     ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);

    void deletePersonnel(String personnelId);

    void deletePersonnelSelected(List<String> idPersonnelsSelected);
    int getPersonnalByMonth();
    int getallPersonnal();
    List<Integer> getPersListe(boolean b);

}
