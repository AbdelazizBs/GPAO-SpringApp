package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.ProduitDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProduitService {
    void addProduit(ProduitDto produit) ;


    void updateProduit(ProduitDto produit,String idProduit) throws ResourceNotFoundException;
    void deleteProduit(String produitId);


    ResponseEntity<Map<String, Object>> getAllProduit(int page, int size);

    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size);
    Produit getProduitByDesignation(String nom) throws ResourceNotFoundException;

    List<Produit> getAllProduitByDesignation(String designation);

    ResponseEntity<Map<String, Object>> onSortProduit(int page, int size, String field, String order);

    List<String> getTypeProduit();

    List<String> getUniteVente();

    ResponseEntity<Map<String, Object>> getAllProduitByType(String type, int page, int size);
}
