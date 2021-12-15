package com.housservice.housstock.service;

import java.util.List;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.dto.UtilisateurDto;

public interface UtilisateurService {
	
	public List<UtilisateurDto> getAllUtilisateur();
	
    public UtilisateurDto getUtilisateurById(String id);
	
    public UtilisateurDto buildUtilisateurDtoFromUtilisateur(Utilisateur utilisateur);

    public void createNewUtilisateur(@Valid UtilisateurDto utilisateurDto);
	
    public void updateUtilisateur(@Valid UtilisateurDto utilisateurDto) throws ResourceNotFoundException;
    
    public void deleteUtilisateur(String utilisateurId);

}
