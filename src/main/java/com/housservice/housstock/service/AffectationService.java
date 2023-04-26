package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.ListeMatiere;
import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AffectationService {
    public ResponseEntity<Map<String, Object>> onSortActiveAffectation(int page, int size, String field, String order);
    void createNewAffectation(AffectationDto affectationDto) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
    public void updateAffectation(AffectationDto affectationDto,String idaffectation) throws ResourceNotFoundException;
    void deleteAffectation(Affectation affectation);
    ResponseEntity<Map<String, Object>> getAllAffectation(int page, int size,String id);
    ResponseEntity<Map<String, Object>> getAllAffectationFrs(int page, int size,String id);

    public List<String> getFournisseur();

    public String getFournisseurRef(String id);

    public Optional<ListeMatiere> getMatiere(String id);
    List<String> getDevise();

    List<String> getUniteAchat();
    void addPrixAchatAffectation(PrixAchatDto prixAchatDto, String idAffectation ) throws ResourceNotFoundException;


    void updatePrixAchatAffectation(PrixAchatDto prixAchat, String idPrixAchat) throws ResourceNotFoundException;

    void deletePrixAchatAffectation(String idPrixAchat) throws ResourceNotFoundException;

    public Optional<Fournisseur> getFournisseurid(String id);

}
