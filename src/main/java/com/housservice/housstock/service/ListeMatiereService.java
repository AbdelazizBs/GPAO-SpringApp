package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.ListeMatiere;

import com.housservice.housstock.model.dto.ListeMatiereDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ListeMatiereService {
    void addListeMatiere(ListeMatiereDto listeMatiere) ;


    void updateListeMatiere(ListeMatiereDto listeMatiere,String idListeMatiere) throws ResourceNotFoundException;
    void deleteListeMatiere(String listeMatiereId);


    List<String> getAllMatiere();

    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size);

    ResponseEntity<Map<String, Object>> getAllMatiereByType(String type,int page, int size);

    ResponseEntity<Map<String, Object>> onSortListeMatiere(int page, int size, String field, String order);

    List<String> getTypePapier();

    List<String> getUniteConsommation();
    int getMatiere();
    int getMatieretype(String type);
}
