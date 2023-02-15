package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface FournisseurService {
	
	ResponseEntity<Map<String, Object>> findFournisseurActif(int page , int size);
	ResponseEntity<Map<String, Object>> findFournisseurNonActive(int page , int size);

	public ResponseEntity<Map<String, Object>>  getIdFournisseurs(String intitule) throws ResourceNotFoundException;

	public List<String> getIntitules();

    Optional<Fournisseur> getFournisseurById(String id);

	void affecteNomEnClatureToFournisseur(  String idFournisseur,
									   List<String>selectedOptions ) throws ResourceNotFoundException;


  void createNewFournisseur(  String refFrsIris,
			  
							 String intitule,
							 
							 String abrege,
								
							 String statut,
									
							 String adresse,
								
							 String codePostal,
								
							 String ville,
								
							 String region,
								
							 String pays,
								
							 String telephone,
								
							 String telecopie,
								
							 String linkedin,
								
							 String email,
								
							 String siteWeb,
								
							 String nomBanque,
								
							 String adresseBanque,
								
							 String rib,
								
							 String swift,
								
							 String codeDouane,
								
							 String rne,
								
							 String identifiantTva,
										
							  MultipartFile[] images) throws ResourceNotFoundException;
	  

	public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
	

	public void updateFournisseur(String idFournisseur ,String refFrsIris,
			
								 String intitule,
								 
								 String abrege,
									
								 String statut,
										
								 String adresse,
									
								 String codePostal,
									
								 String ville,
									
								 String region,
									
								 String pays,
									
								 String telephone,
									
								 String telecopie,
									
								 String linkedin,
									
								 String email,
									
								 String siteWeb,
									
								 String nomBanque,
									
								 String adresseBanque,
									
								 String rib,
									
								 String swift,
									
								 String codeDouane,
									
								 String rne,
									
								 String identifiantTva,

							     MultipartFile[] images) throws ResourceNotFoundException;
	
	 void miseEnVeille(String idFournisseur ) throws ResourceNotFoundException;
	 
	 void addContactFournisseur( ContactDto contactDto, String idFournisseur ) throws ResourceNotFoundException;
	 
	 void updateContactFournisseur( ContactDto contactDto, String idContact) throws ResourceNotFoundException;

	 void deleteFournisseur(Fournisseur fournisseur);
	 
	 void deleteFournisseurSelected(List<String> idFournisseursSelected);

	 void deleteContactFournisseur(String idContact) throws ResourceNotFoundException;
	 
	 void removePictures(String idFournisseur) throws ResourceNotFoundException;

	 void removePicture(String idPic) throws ResourceNotFoundException;
       

}
