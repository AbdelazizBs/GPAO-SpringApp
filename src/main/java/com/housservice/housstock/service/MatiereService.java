package com.housservice.housstock.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;

public interface MatiereService {
	
	public ResponseEntity<Map<String, Object>> getAllMatiere(int page, int size);
	public ResponseEntity<Map<String, Object>> getAllMatiereEnVeille(int page, int size);
 
    public MatiereDto getMatiereById(String id) throws ResourceNotFoundException;
    public Matiere getMatiereByRefMatiereIris(String refMatiereIris) throws ResourceNotFoundException;
    public Matiere getMatiereByDesignation(String designation) throws ResourceNotFoundException;
    
    // add new matiereDto
    void addMatiere(MatiereDto matiereDto) ;
    
    // update matiereDto
    void updateMatiere(MatiereDto matiereDto,String idMatiere) throws ResourceNotFoundException;

    void mettreEnVeille(String idMatiere) throws ResourceNotFoundException;
    
    public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size,boolean enVeille);

    void deleteMatiere(String matiereId);
    
    void deleteMatiereSelected(List<String> idMatieresSelected);


}
