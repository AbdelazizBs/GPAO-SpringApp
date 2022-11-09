package com.housservice.housstock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.springframework.http.ResponseEntity;

public interface PersonnelService {
	
	public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size);
	
    public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException;
    public Personnel getPersonnelByEmail(String email);
    public Personnel getPersonnelByNom(String nom);

    public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel) throws ResourceNotFoundException;

    public void createNewPersonnel(String nom,
                                     String prenom,
                                     Date dateDeNaissance,
                                     String adresse,
                                     String photo,
                                     String cin,
                                     String sexe,
                                     String rib,
                                     String poste,
                                     Date datedembauche,
                                   int echelon,
                                   int matricule,
                                     String categorie
                                     ) throws ResourceNotFoundException;
	
     void updatePersonnel(@Valid PersonnelDto personnelDto) throws ResourceNotFoundException;

    void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size);
    public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size);

    void deletePersonnel(String utilisateurId);

}
