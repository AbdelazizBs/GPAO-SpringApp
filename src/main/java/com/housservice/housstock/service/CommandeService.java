package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    Optional<Commande> getCommandeById(String id);

    void createNewCommande(String date, String commentaire, String numBcd, String Fournisseur) throws ResourceNotFoundException;
    void UpdateCommande(String idCommande,String commentaire, String numBcd, String Fournisseur,String date) throws ResourceNotFoundException;
    void deleteCommande(Commande commande);
    void deleteCommandeSelected(List<String> idCommandesSelected);
    public ResponseEntity<Map<String, Object>> getIdCommandes(String numBcd) throws ResourceNotFoundException;
    int getallCommande();
    public ResponseEntity<Map<String, Object>> onSortActiveCommande(int page, int size, String field, String order);
    List<String> getAllFournisseurs();
    ResponseEntity<Map<String, Object>> getAllCommande(int page , int size);
    void addArticleCommande(ArticleDto articleDto, String idCommande ) throws ResourceNotFoundException;
    void updateArticleCommande( ArticleDto article, String idArticle) throws ResourceNotFoundException;
    void deleteArticleCommande(String idArticle) throws ResourceNotFoundException;
    List<String> getAllMatiere();
    void addMatiere(String designation)throws ResourceNotFoundException;


}