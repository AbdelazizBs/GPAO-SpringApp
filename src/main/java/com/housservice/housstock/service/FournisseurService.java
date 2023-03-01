package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FournisseurService {

    ResponseEntity<Map<String, Object>> getActiveFournisseur(int page , int size);
    ResponseEntity<Map<String, Object>> getFournisseurNotActive(int page , int size);

    public ResponseEntity<Map<String, Object>>  getIdFournisseurs(String raisonSociale) throws ResourceNotFoundException;

    public List<String> getRaisonSociales();

    Optional<Fournisseur> getFournisseurById(String id);


    public ResponseEntity<Map<String, Object>> onSortActiveFournisseur(int page, int size, String field, String order);
    public ResponseEntity<Map<String, Object>> onSortFournisseurNotActive(int page, int size, String field, String order);


    void createNewFournisseur(  String refFournisseurIris,
                           String raisonSociale,
                           String adresse,
                           String codePostal,
                           String ville,
                           String pays,
                           String region,
                           String phone,
                           String email,
                           String statut,
                           String linkedin,
                           String abrege,
                           String siteWeb,
                           String nomBanque,
                           String adresseBanque,
                           String codeDouane,
                           String rne,
                           String identifiantTva,
                           String telecopie,
                           String rib,
                           String swift,
                           MultipartFile[] images) throws ResourceNotFoundException;
    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);




    public void updateFournisseur(String idFournisseur ,String refFournisseurIris,
                             String raisonSociale,
                             String adresse,
                             String codePostal,
                             String ville,
                             String pays,
                             String region,
                             String phone,
                             String email,
                             String statut,
                             String linkedin,
                             String abrege,
                             String siteWeb,
                             String nomBanque,
                             String adresseBanque,
                             String codeDouane,
                             String rne,
                             String identifiantTva,
                             String telecopie,
                             String rib,
                             String swift,
                             MultipartFile[] images) throws ResourceNotFoundException;
    void miseEnVeille(String idFournisseur ) throws ResourceNotFoundException;
    void addContactFournisseur( ContactDto contactDto, String idFournisseur ) throws ResourceNotFoundException;
    void updateContactFournisseur( ContactDto contact, String idContact) throws ResourceNotFoundException;

    void deleteFournisseur(Fournisseur fournisseur);
    void deleteFournisseurSelected(List<String> idFournisseursSelected);

    void deleteContactFournisseur(String idContact) throws ResourceNotFoundException;
    void removePictures(String idFournisseur) throws ResourceNotFoundException;

    void removePicture(String idPicture) throws ResourceNotFoundException;
    public ResponseEntity<byte[]> RecordReport(String refFournisseurIris);
}
