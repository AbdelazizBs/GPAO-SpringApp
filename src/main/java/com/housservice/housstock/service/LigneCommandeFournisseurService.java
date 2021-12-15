package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeFournisseur;
import com.housservice.housstock.model.dto.LigneCommandeFournisseurDto;

public interface LigneCommandeFournisseurService {

    public List<LigneCommandeFournisseurDto> getAllLigneCommandeFournisseur();
	
    public LigneCommandeFournisseurDto getLigneCommandeFournisseurById(String id);
	
    public LigneCommandeFournisseurDto buildLigneCommandeFournisseurDtoFromLigneCommandeFournisseur(LigneCommandeFournisseur ligneCommandeFournisseur);

    public void createNewLigneCommandeFournisseur(@Valid LigneCommandeFournisseurDto ligneCommandeFournisseurDto);
	
    public void updateLigneCommandeFournisseur(@Valid LigneCommandeFournisseurDto ligneCommandeFournisseurDto) throws ResourceNotFoundException;
    
    public void deleteLigneCommandeFournisseur(String ligneCommandeFournisseurId);
}
