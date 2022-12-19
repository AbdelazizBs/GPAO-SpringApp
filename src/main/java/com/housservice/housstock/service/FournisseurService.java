package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;


public interface FournisseurService {
	
	 public ResponseEntity<Map<String, Object>> getAllFournisseur(int page, int size);
	 
	 public FournisseurDto getfournisseurById(String id) throws ResourceNotFoundException;
	 
	 public Fournisseur getFournisseurByIntitule(String intitule) throws ResourceNotFoundException;
	
	 public FournisseurDto buildFournisseurDtoFromFournisseur(Fournisseur fournisseur) throws ResourceNotFoundException;
	
	 public void createNewFournisseur( String refFrsIris,

				 String intitule,

				 String abrege,

				 String interlocuteur,
				
				 String adresse,

				 String codePostal,

				 String ville,

				 String region,
				
				 String pays,
				
				 String telephone,
				
				 String telecopie,
				
				 String linkedin,
				
				 String email,
				
				 String siteInternet,
				
				 String identifiantTva

				 
              ) throws ResourceNotFoundException;
	 
	  void updateNewFournisseur( String idFournisseur,
			  
			  String refFrsIris,

			 String intitule,

			 String abrege,

			 String interlocuteur,
			
			 String adresse,

			 String codePostal,

			 String ville,

			 String region,
			
			 String pays,
			
			 String telephone,
			
			 String telecopie,
			
			 String linkedin,
			
			 String email,
			
			 String siteInternet,
			
			 String identifiantTva

			 
          ) throws ResourceNotFoundException;
	  
	  void mettreEnVeille(String idFournisseur) throws ResourceNotFoundException;
	  
	  public ResponseEntity<Map<String, Object>> getAllFournisseurEnVeille(int page, int size);
	  public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size,boolean enVeille);
	  
	  void deleteFournisseur(String fournisseurId);
	  void deleteFournisseurSelected(List<String> idFournisseursSelected);


}
