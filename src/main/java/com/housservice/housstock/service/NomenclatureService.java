package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface NomenclatureService {

	ResponseEntity<Map<String, Object>> getFamilleNomenclature(int page , int size);
	ResponseEntity<Map<String, Object>> getAllNomenClatures(int page , int size);
	ResponseEntity<Map<String, Object>> getRow(List<String> childrenIds);
	ResponseEntity<Map<String, Object>> getNomenClaturesByRaisonsClient(String raison) throws ResourceNotFoundException;

	ResponseEntity<Map<String, Object>> findNomenclatureNonActive(int page , int size);

	public ResponseEntity<Map<String, Object>>  getIdNomenclatures(String nomNomenclature) throws ResourceNotFoundException;

	public List<String> getNomNomenclatures();

    Optional<Nomenclature> getNomenclatureById(String id);

	 List<String> getParent();
	 List<String> getParentsNameFiltered(String idNomenclature) throws ResourceNotFoundException;


	void createNewNomenclature(  String nomNomenclature,
								 List<String> parentsName,
			  
			 String description,

								 String type,
				
			 String nature,
					
			 String categorie,
								
			 MultipartFile[] images) throws ResourceNotFoundException;


    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);


    public void updateNomenclature(String idNomenclature ,
		
			 String nomNomenclature,
			
			 String description,
			 
			 String type,
				
			 String nature,
					
			 String categorie,
			 List<String> parentsName,

			 MultipartFile[] images) throws ResourceNotFoundException;


    void miseEnVeille(String idNomenclature ) throws ResourceNotFoundException;

    void deleteNomenclature(Nomenclature nomenclature);

    void deleteNomenclatureSelected(List<String> idNomenclaturesSelected);

    void removePictures(String idNomenclature) throws ResourceNotFoundException;

    void removePicture(String idPic) throws ResourceNotFoundException;
	void deleteNomenclatureEnVeilleSelected(List<String> idNomenClatureSelected);


	public List<Nomenclature> getLigneSousFamilleByIdFamille(String idNomEnClature) throws ResourceNotFoundException;

}
