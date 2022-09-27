package com.housservice.housstock.service;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.dto.UtilisateurDto;

public interface UtilisateurService {
	
	public List<UtilisateurDto> getAllUtilisateur();
	
    public UtilisateurDto getUtilisateurById(String id);
    public Utilisateur getUtilisateurByEmail(String email);
    public Utilisateur getUtilisateurByNom(String nom);

    public UtilisateurDto buildUtilisateurDtoFromUtilisateur(Utilisateur utilisateur);

    public void createNewUtilisateur(String nom,
                                     String prenom,
                                     Date dateDeNaissance,
                                     String adresse,
                                     String photo,
                                     String email,
                                     String password
                                     ) throws ResourceNotFoundException;
	
    public void updateUtilisateur(@Valid UtilisateurDto utilisateurDto) throws ResourceNotFoundException;
    
    public void deleteUtilisateur(String utilisateurId);

}
