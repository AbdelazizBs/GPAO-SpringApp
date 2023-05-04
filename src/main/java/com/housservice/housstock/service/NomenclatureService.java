package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface NomenclatureService {

    ResponseEntity<Map<String, Object>> getFamilleNomenclature(int page, int size);

    ResponseEntity<Map<String, Object>> getAllNomenClatures(int page, int size);

    ResponseEntity<Map<String, Object>> getRow(List<String> childrenIds);

    ResponseEntity<Map<String, Object>> getNomenClaturesByRaisonsClient(String raison) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> getNomenClaturesByIntituleFournisseur(String intitule) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> findNomenclatureNonActive(int page, int size);

    ResponseEntity<Map<String, Object>> onSortNomenclatureNotActive(int page, int size, String field, String order);

    ResponseEntity<Map<String, Object>> onSortActiveNomenClature(int page, int size, String field, String order);


    public List<String> getNomNomenclatures();

    public List<String> getNameOfNomenclatureOfClient(String idClient) throws ResourceNotFoundException;

    public List<String> getIdAndRefIrisOfNomenclature(String nameNomenclature) throws ResourceNotFoundException;

    Optional<Nomenclature> getNomenclatureById(String id);

    ResponseEntity<Map<String, Object>> getParent();

    ResponseEntity<Map<String, Object>> getChildrensName();
    ResponseEntity<Map<String, Object>> getElementOfNomenclature(String idNomenclature) throws ResourceNotFoundException;


    ResponseEntity<Map<String, Object>> getSelectedChildrensName(String nomenclatureId) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getSelectedChildrens(List<String> nomNomenclature) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> getChildrensNomenclatureToUpdate(String nomenclatureId) throws ResourceNotFoundException;


    List<String> getNomenclatureNameAffectedForClient(String idClient) throws ResourceNotFoundException;

    List<String> getNomenclatureNameAffectedForFrs(String idFrs) throws ResourceNotFoundException;

    List<String> getNomenclaturesName();

    List<String> getNomenClaturesNameClient();

    List<String> getNomenclaturesNameFrs();

    void createNewNomenclature(String nomNomenclature,
                               List<String> parentsName,
                               List<String> childrensName,

                               String description,
                               String refiIris,

                               String type,

                               String nature,
                               String categorie,
                               Date durationOfFabrication,
                               int quantity,
                               int quantityMax,
                               int quantityMin,
                               List<String> etapeProductions,

                               MultipartFile[] image) throws ResourceNotFoundException, IOException;


    ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille);


    void updateNomenclature(String idNomenclature,

                            String nomNomenclature,

                            String description,
                            String refIris,

                            String type,

                            String nature,

                            String categorie,
                            List<String> parentsName,
                            List<String> childrensName,
                            Date durationOfFabrication,
                            int quantity,
                            int quantityMax,
                            int quantityMin,
                            List<String> etapeProductions,

                            MultipartFile[] image) throws ResourceNotFoundException;

    void affectClientAndFrsToNomenclature(String idNomenclature,
                                          List<String> raisonSoClient,
                                          List<String> intituleFrs) throws ResourceNotFoundException;


    void miseEnVeille(String idNomenclature) throws ResourceNotFoundException;

    void deleteNomenclature(Nomenclature nomenclature);

    void deleteNomenclatureSelected(List<String> idNomenclaturesSelected);

    void deleteNomenclatureEnVeilleSelected(List<String> idNomenClatureSelected);


    List<Nomenclature> getLigneSousFamilleByIdFamille(String idNomEnClature) throws ResourceNotFoundException;

    void addEtapeToNomenclature(List<EtapeProduction> etapeProductions, String idNomenclature) throws ResourceNotFoundException;

    List<EtapeProduction> getTargetEtapesNomenclature(String idNomenclature) throws ResourceNotFoundException;


}
