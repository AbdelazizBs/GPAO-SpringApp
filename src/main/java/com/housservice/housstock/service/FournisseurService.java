package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;

public interface FournisseurService {
	
	public List<FournisseurDto> getAllFournisseur();
	
    public FournisseurDto getFournisseurById(String id);
	
    public FournisseurDto buildFournisseurDtoFromFournisseur(Fournisseur fournisseur);

    public void createNewFournisseur(@Valid FournisseurDto fournisseurDto);
	
    public void updateFournisseur(@Valid FournisseurDto fournisseurDto) throws ResourceNotFoundException;
    
    public void deleteFournisseur(String fournisseurId);

}
