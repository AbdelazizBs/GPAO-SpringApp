package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.CommandeSuivi;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.CommandeDto;
import com.housservice.housstock.model.dto.CommandeSuiviDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    Optional<Commande> getCommandeById(String id);
    ResponseEntity<Map<String, Object>> getAllCommande(int page , int size);
    void createNewCommande(CommandeDto commandeDto) throws ResourceNotFoundException;
    void UpdateCommande(Commande commande,  String id) throws ResourceNotFoundException;
    void deleteCommande(Commande commande);
    void deleteCommandeSelected(List<String> idCommandesSelected);
    public ResponseEntity<Map<String, Object>> getIdCommandes(String numBcd) throws ResourceNotFoundException;
    int getallCommande();
    public ResponseEntity<Map<String, Object>> onSortActiveCommande(int page, int size, String field, String order);
    List<String> getAllFournisseurs();
    ResponseEntity<byte[]> RecordReport(String id);
    void addArticleCommande(ArticleDto articleDto, String idCommande ) throws ResourceNotFoundException;
    void updateArticleCommande( ArticleDto article, String idArticle) throws ResourceNotFoundException;
    void deleteArticleCommande(String idArticle) throws ResourceNotFoundException;
    List<String> getAllMatiere();
    void addMatiere(String designation)throws ResourceNotFoundException;
    void miseEnVeille(String id, CommandeSuiviDto commandeSuiviDto) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getCommandeNotActive(int page, int size);
    ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
     List<String> getAllArticle(String nomClient,String type);
    public void delete(String id) ;
}