package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ResponseEntity<Map<String, Object>> getIdNomenclatures(String nomNomenclature) throws ResourceNotFoundException;

    public List<String> getNomNomenclatures();

    public List<String> getNameOfNomenclatureOfClient(String idClient) throws ResourceNotFoundException;

    public List<String> getIdAndRefIrisOfNomenclature(String nameNomenclature) throws ResourceNotFoundException;

    Optional<Nomenclature> getNomenclatureById(String id);

    ResponseEntity<Map<String, Object>> getParent();

    List<String> getParentsNameFiltered(String idNomenclature) throws ResourceNotFoundException;

    List<String> getNomenclatureNameAffectedForClient(String idClient) throws ResourceNotFoundException;

    List<String> getNomenclatureNameAffectedForFrs(String idFrs) throws ResourceNotFoundException;

    List<String> getNomenclaturesName();

    List<String> getNomenClaturesNameClient();

    List<String> getNomenclaturesNameFrs();

    void createNewNomenclature(String nomNomenclature,
                               List<String> parentsName,

                               String description,
                               String refiIris,

                               String type,

                               String nature,
                               String categorie,
                               List<String> raisonSoClient,
                               List<String> intituleFrs,
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
                            List<String> raisonSoClient,
                            List<String> intituleFrs,

                            MultipartFile[] image) throws ResourceNotFoundException;


    void miseEnVeille(String idNomenclature) throws ResourceNotFoundException;

    void deleteNomenclature(Nomenclature nomenclature);

    void deleteNomenclatureSelected(List<String> idNomenclaturesSelected);

    void deleteNomenclatureEnVeilleSelected(List<String> idNomenClatureSelected);


    List<Nomenclature> getLigneSousFamilleByIdFamille(String idNomEnClature) throws ResourceNotFoundException;

    void addEtapeToNomenclature(List<EtapeProduction> etapeProductions, String idNomenclature) throws ResourceNotFoundException;

    List<EtapeProduction> getTargetEtapesNomenclature(String idNomenclature) throws ResourceNotFoundException;


}
