package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AffectationService {
    void addPrixAchatAffectation(PrixAchatDto prixAchatDto, String idAffectation ) throws ResourceNotFoundException;


    void updatePrixAchatAffectation(PrixAchatDto prixAchat, String idPrixAchat) throws ResourceNotFoundException;

    void deletePrixAchatAffectation(String idPrixAchat) throws ResourceNotFoundException;
    void addAffectation(AffectationDto affectationDto) ;
    void updateAffectation(AffectationDto affectationDto, String idAffectation) throws ResourceNotFoundException;
   ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size);
    ResponseEntity<Map<String, Object>> onSortAffectation(int page, int size, String field, String order);
    List<String> getDevise();

    List<String> getUniteAchat();
    Optional<Affectation> getAffectationByIdMatiere(String idMatiere);

}
