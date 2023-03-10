package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.Fournisseur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    Optional<Commande> getCommandeById(String id);
    ResponseEntity<Map<String, Object>> getAllCommande(int page , int size);
    void createNewCommande(String fournisseur, String numBcd  , String dateCommande,String commentaire) throws ResourceNotFoundException;
    void UpdateCommande(String dateCommande,String commentaire, String numBcd, String Fournisseur,String id) throws ResourceNotFoundException;
    void deleteCommande(Commande commande);
    void deleteCommandeSelected(List<String> idCommandesSelected);
    public ResponseEntity<Map<String, Object>> getIdCommandes(String numBcd) throws ResourceNotFoundException;
    int getallCommande();
    public ResponseEntity<Map<String, Object>> onSortActiveCommande(int page, int size, String field, String order);
    List<String> getAllFournisseurs();
    ResponseEntity<byte[]> RecordReport(String id);

}