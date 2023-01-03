package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;


public interface FournisseurService {
	
	public ResponseEntity<Map<String, Object>> getAllFournisseur(int page, int size);
    public ResponseEntity<Map<String, Object>> getAllFournisseurEnVeille(int page, int size);

    public FournisseurDto getFournisseurById(String id) throws ResourceNotFoundException;
    public Fournisseur getFournisseurByIntitule(String intitule) throws ResourceNotFoundException;

    void addFournisseur(FournisseurDto fournisseurDto) ;
 
    void updateFournisseur(FournisseurDto fournisseurDto,String idFournisseur) throws ResourceNotFoundException;

    void mettreEnVeille(String idFournisseur) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size,boolean enVeille);

    void deleteFournisseur(String fournisseurId);
    
    void deleteFournisseurSelected(List<String> idFournisseursSelected);

}
