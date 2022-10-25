package com.housservice.housstock.service;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.model.dto.PersonnelDto;

public interface PersonnelService {
	
	public List<PersonnelDto> getAllPersonnel();
	
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
                                     String echelon,
                                     String category
                                     ) throws ResourceNotFoundException;
	
     void updatePersonnel(@Valid PersonnelDto personnelDto) throws ResourceNotFoundException;

     void deletePersonnel(String utilisateurId);

}
