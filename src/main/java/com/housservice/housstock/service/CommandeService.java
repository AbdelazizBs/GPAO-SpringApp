package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Commande;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    Optional<Commande> getCommandeById(String id);

    void createNewCommande(Date date, String commentaire, String numBcd, String Fournisseur) throws ResourceNotFoundException;
    void UpdateCommande(String idCommande,String commentaire, String numBcd, String Fournisseur) throws ResourceNotFoundException;
    void deleteCommande(Commande commande);
    void deleteCommandeSelected(List<String> idCommandesSelected);
    public ResponseEntity<Map<String, Object>> getIdCommandes(String numBcd) throws ResourceNotFoundException;
    int getallCommande();
    public ResponseEntity<Map<String, Object>> onSortActiveCommande(int page, int size, String field, String order);
    List<String> getAllFournisseurs();
    ResponseEntity<Map<String, Object>> getAllCommande(int page , int size);

}