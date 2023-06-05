package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.ProduitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProduitService {
    void addProduit(ProduitDto produit) ;
    void addEtape(String[] Etapes,String id) ;


    void updateProduit(ProduitDto produit,String idProduit) throws ResourceNotFoundException;
    void deleteProduit(String produitId);


    ResponseEntity<Map<String, Object>> getAllProduit(int page, int size);

    ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size);
    Produit getProduitByDesignation(String nom) throws ResourceNotFoundException;

    List<Produit> getAllProduitByDesignation(String designation);

    ResponseEntity<Map<String, Object>> onSortProduit(int page, int size, String field, String order);

    List<String> getTypeProduit();
    List<String> getEtape();
   String[] getEtapes(String id);
    List<String> getUniteVente();

    ResponseEntity<Map<String, Object>> getAllProduitByType(String type, int page, int size);
    void addphoto(MultipartFile[] images, String ref);
    void removePictures(String idP) throws ResourceNotFoundException;
    void miseEnVeille(String idArticle ) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getAllProduitvielle(int page, int size) ;
    void removePicture(String idPicture) throws ResourceNotFoundException;
     void Restaurer(String id) throws ResourceNotFoundException ;
    int getArticleBytype(String type);
    int getallArticle();
    List<Produit> getallProduitlist();
}
