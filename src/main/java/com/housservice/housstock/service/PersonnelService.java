package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

public interface PersonnelService {
	
	public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size);
	
    public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException;
    public Personnel getPersonnelByEmail(String email) throws ResourceNotFoundException;
    public Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException;

    public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel) throws ResourceNotFoundException;

    public void createNewPersonnel(String nom,
                                     String prenom,
                                   LocalDate dateDeNaissance,
                                     String adresse,
                                   String matricule,
                                   String photo,
                                     String cin,
                                     String sexe,
                                     String rib,
                                     String poste,
                                   LocalDate datedembauche,
                                   String phone,
                                     String categorie,
                                   String ville,
                                   String codePostal,
                                   String email
                                     ) throws ResourceNotFoundException;
	
     void updatePersonnel(String idPersonnel,
                            String nom,
                          String prenom,
                          LocalDate dateDeNaissance,
                          String adresse,
                          String matricule,
                          String photo,
                          String cin,
                          String sexe,
                          String rib,
                          String poste,
                          LocalDate datedembauche,
                          int echelon,
                          String phone,
                          String categorie,
                          String ville,
                          String codePostal,
                                        String email
     ) throws ResourceNotFoundException;

    void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size);
    public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size);

    void deletePersonnel(String utilisateurId);

}
