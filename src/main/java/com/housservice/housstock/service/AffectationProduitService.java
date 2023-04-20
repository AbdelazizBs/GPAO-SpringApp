package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.AffectationProduit;
import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.AffectationProduitDto;
import com.housservice.housstock.model.dto.PrixVenteDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AffectationProduitService {
    void addPrixVenteAffectationProduit(PrixVenteDto prixVenteDto, String idAffectationProduit ) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getAllAffectation(int page, int size,String id);


    void updatePrixVenteAffectationProduit(PrixVenteDto prixVente, String idPrixVente) throws ResourceNotFoundException;

    void deletePrixVenteAffectationProduit(String idPrixVente) throws ResourceNotFoundException;
    void addAffectationProduit(AffectationProduitDto affectationProduitProduitDto) ;
    void updateAffectationProduit(AffectationProduitDto affectationProduitProduitDto, String idAffectationProduit) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size);
    ResponseEntity<Map<String, Object>> onSortAffectationProduit(int page, int size, String field, String order);
    Optional<AffectationProduit> getAffectationProduitByIdProduit(String idMatiere);
    List<String> getClient();
    String getClientRef(String id);
    void deleteAffectation(AffectationProduit affectationProduit);
    Optional<Produit> getProduit(String id);

}
