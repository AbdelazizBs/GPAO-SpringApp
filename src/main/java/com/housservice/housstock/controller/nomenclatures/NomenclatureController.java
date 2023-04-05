package com.housservice.housstock.controller.nomenclatures;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.service.NomenclatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/nomenclature")
@Api(tags = {"Nomenclatures Management"})
@Validated
public class NomenclatureController {

    private final NomenclatureService nomenclatureService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    @Autowired
    public NomenclatureController(NomenclatureService nomenclatureService, MessageHttpErrorProperties messageHttpErrorProperties) {

        this.nomenclatureService = nomenclatureService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;

    }

    @GetMapping("/getFamilleNomenclature")
    @ApiOperation(value = "service to get famille les nomenclatures ")
    public ResponseEntity<Map<String, Object>> getAllNomenclature(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return nomenclatureService.getFamilleNomenclature(page, size);

    }

    @GetMapping("/getAllNomenClatures")
    @ApiOperation(value = "service to get tout les nomenclatures ")
    public ResponseEntity<Map<String, Object>> getAllNomenClatures(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return nomenclatureService.getAllNomenClatures(page, size);

    }

    @GetMapping("/getRow")
    @ApiOperation(value = "service to get childrens of nomenclature")
    public ResponseEntity<Map<String, Object>> getRow(@RequestParam(value = "childrensId") List<String> childrenIds) {
        return nomenclatureService.getRow(childrenIds);
    }

    @GetMapping("/getNomenClaturesByRaisonsClient/{raisonS}")
    @ApiOperation(value = "service to get one Client by Id.")
    public ResponseEntity<Map<String, Object>> getNomenClaturesByRaisonsClient(
            @ApiParam(name = "raisonS", value = "raison sociale of client", required = true)
            @PathVariable(value = "raisonS", required = true) @NotEmpty(message = "{http.error.0001}") String raisonS)
            throws ResourceNotFoundException {
        return nomenclatureService.getNomenClaturesByRaisonsClient(raisonS);
    }

    @GetMapping("/getNomenClaturesByIntituleFournisseur/{intitule}")
    @ApiOperation(value = "service to get one Client by Id.")
    public ResponseEntity<Map<String, Object>> getNomenClaturesByIntituleFournisseur(
            @ApiParam(name = "intitule", value = "intitule  fournisseur", required = true)
            @PathVariable(value = "intitule", required = true) @NotEmpty(message = "{http.error.0001}") String intitule)
            throws ResourceNotFoundException {
        return nomenclatureService.getNomenClaturesByIntituleFournisseur(intitule);
    }

    @GetMapping("/nomenclature/{id}")
    @ApiOperation(value = "service to get one Nomenclature by Id.")
    public ResponseEntity<Nomenclature> getNomenclatureById(
            @ApiParam(name = "id", value = "id of nomenclature", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
            throws ResourceNotFoundException {

        Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));
        return ResponseEntity.ok().body(nomenclature);
    }


    @GetMapping("/getIdNomenclatures/{NomNomenclature}")
    @ApiOperation(value = "service to get Id Nomenclature by nomNomenclature.")
    public ResponseEntity<Map<String, Object>> getIdNomenclatures(@ApiParam(name = "nomNomenclature", value = "nomNomenclature of Nomenclatures", required = true)
                                                                  @PathVariable(value = "nomNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String nomNomenclature) throws ResourceNotFoundException {
        return nomenclatureService.getIdNomenclatures(nomNomenclature);
    }


    @GetMapping("/getNomNomenclatures")
    @ApiOperation(value = "service to get one NomNomenclature")
    public List<String> getNomNomenclatures() {
        return nomenclatureService.getNomNomenclatures();
    }

    @GetMapping("/getNameOfNomenclatureOfClient/{idClient}")
    @ApiOperation(value = "service to get Names Of Nomenclature Of Client.")
    public List<String> getNameOfNomenclatureOfClient(
            @ApiParam(name = "idClient", value = "id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient)
            throws ResourceNotFoundException {
        return nomenclatureService.getNameOfNomenclatureOfClient(idClient);
    }

    @GetMapping("/getIdAndRefIrisOfNomenclature/{nameNomenclature}")
    @ApiOperation(value = "service to get Ref Iris And Client  .")
    public List<String> getIdAndRefIrisOfNomenclature(
            @ApiParam(name = "nameNomenclature", value = "designation of nomenclature", required = true)
            @PathVariable(value = "nameNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String nameNomenclature)
            throws ResourceNotFoundException {
        return nomenclatureService.getIdAndRefIrisOfNomenclature(nameNomenclature);
    }

    @PutMapping("/addEtapeToNomenclature/{idNomenclature}")
    public ResponseEntity<String> addEtapeToNomenclature(
            @ApiParam(name = "idNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature,
            @Valid @RequestBody(required = true) List<EtapeProduction> productions) throws ResourceNotFoundException {
        nomenclatureService.addEtapeToNomenclature(productions, idNomenclature);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @GetMapping("/getAllNomenclatureNotActive")
    public ResponseEntity<Map<String, Object>> getAllNomenclatureNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return nomenclatureService.findNomenclatureNonActive(page, size);

    }

    @GetMapping("/onSortNomenclatureNotActive")
    @ApiOperation(value = "service to get get All  Personnel not active ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortNomenclatureNotActive(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "3") int size,
                                                                           @RequestParam(defaultValue = "field") String field,
                                                                           @RequestParam(defaultValue = "order") String order) {
        return nomenclatureService.onSortNomenclatureNotActive(page, size, field, order);

    }

    @GetMapping("/onSortActiveNomenClature")
    @ApiOperation(value = "service to get get All active Nomenclature   ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActiveNomenClature(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size,
                                                                        @RequestParam(defaultValue = "field") String field,
                                                                        @RequestParam(defaultValue = "order") String order) {
        return nomenclatureService.onSortActiveNomenClature(page, size, field, order);

    }

    @GetMapping("/search")
    @ApiOperation(value = "service to filter nomenclatures ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return nomenclatureService.search(textToFind, page, size, enVeille);

    }


    @DeleteMapping("/deleteSelectedNomenclature/{idNomenclaturesSelected}")
    @ApiOperation(value = "service to delete many Nomenclature by Id.")
    public Map<String, Boolean> deleteNomenclatureSelected(
            @ApiParam(name = "idNomenclaturesSelected", value = "ids of nomenclature Selected", required = true) @PathVariable(value = "idNomenclaturesSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idNomenclaturesSelected) {
        nomenclatureService.deleteNomenclatureSelected(idNomenclaturesSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(value = "/addNomenclature")
    public ResponseEntity<String> createNewNomenclature(
            @RequestParam("nomNomenclature")
            @NotEmpty(message = "champ Nom nomenclature obligatoire")
            String nomNomenclature,
            @RequestParam("description")
            String description,
            @RequestParam("refIris")
            String refiIris,
            @RequestParam("raisonSoClient")
            List<String> raisonSoClient,
            @RequestParam("intituleFrs")
            List<String> intituleFrs,
            @RequestParam("type")
            @NotEmpty(message = "champ type obligatoire")
            String type,
            @RequestParam("nature")
            String nature,
            @RequestParam("categorie")
            @NotEmpty(message = "champ categorie obligatoire")
            String categorie,
            @RequestParam("parentsName")
            List<String> parentsName,
            @RequestParam("childrensName")
            List<String> childrensName,
            @RequestParam("image") MultipartFile[] image
    ) throws ResourceNotFoundException, IOException {

        nomenclatureService.createNewNomenclature(nomNomenclature, parentsName, childrensName, description, refiIris, type, nature, categorie,
                raisonSoClient, intituleFrs, image);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }


    @GetMapping("/getLigneSousFamilleByIdFamille/{idNomEnClature}")
    public List<Nomenclature> getLigneSousFamilleByIdFamille(
            @PathVariable(value = "idNomEnClature") String idNomEnClature) throws ResourceNotFoundException {
        return nomenclatureService.getLigneSousFamilleByIdFamille(idNomEnClature);
    }

    @DeleteMapping("/deleteNomenclatureEnVeilleSelected/{idNomenClatureSelected}")
    @ApiOperation(value = "service to delete many Personnel by Id.")
    public Map<String, Boolean> deleteNomenclatureEnVeilleSelected(
            @ApiParam(name = "idNomenClatureSelected", value = "ids of nomenclatures Selected", required = true) @PathVariable(value = "idNomenClatureSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idNomenClatureSelected) {
        nomenclatureService.deleteNomenclatureEnVeilleSelected(idNomenClatureSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @PutMapping("/updateNomenclature/{idNomenclature}")
    public ResponseEntity<String> updateNomenclature(
            @ApiParam(name = "idNomenclature", value = "id of Nomenclature", required = true)
            @PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature,
            @RequestParam("nomNomenclature") String nomNomenclature,
            @RequestParam("description") String description,
            @RequestParam("refIris") String refIris,
            @RequestParam("type") String type,
            @RequestParam("nature") String nature,
            @RequestParam("categorie") String categorie,
            @RequestParam("parentsName") List<String> parentsName,
            @RequestParam("childrensName") List<String> childrensName,
            @RequestParam("raisonSoClient") List<String> raisonSoClient,
            @RequestParam("intituleFrs") List<String> intituleFrs,
            @RequestParam("image") MultipartFile[] image) throws ResourceNotFoundException {
        nomenclatureService.updateNomenclature(idNomenclature, nomNomenclature, description, refIris, type, nature, categorie, parentsName, childrensName, raisonSoClient, intituleFrs, image);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @PutMapping("/miseEnVeille/{idNomenclature}")
    public ResponseEntity<String> miseEnVeille(
            @ApiParam(name = "idNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature,
            @RequestBody(required = true) NomenclatureDto nomenclatureDto) throws ResourceNotFoundException {

        nomenclatureService.miseEnVeille(idNomenclature);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @DeleteMapping("/deleteNomenclature/{id}")
    @ApiOperation(value = "service to delete one Nomenclature by Id.")
    public Map<String, Boolean> deleteclient(
            @ApiParam(name = "id", value = "id of nomenclature", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
            throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));

        nomenclatureService.deleteNomenclature(nomenclature);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @GetMapping("/getChildrensNameElements")
    @ApiOperation(value = "service to get cjhildrens name type element  of nomEnClature")
    public ResponseEntity<Map<String, Object>> getChildrensNameElements() {
        return nomenclatureService.getChildrensNameElements();
    }

    @GetMapping("/getChildrensNameArticles")
    @ApiOperation(value = "service to get cjhildrens name  type Article  of nomEnClature")
    public ResponseEntity<Map<String, Object>> getChildrensNameArticles() {
        return nomenclatureService.getChildrensNameArticles();
    }

    @GetMapping("/getParent")
    @ApiOperation(value = "service to get parents name of nomEnClature")
    public ResponseEntity<Map<String, Object>> getParent() {
        return nomenclatureService.getParent();
    }

    @GetMapping("/getSelectedChildrensNmae/{nomenclatureId}")
    @ApiOperation(value = "service to get names Elements by Id nomenclature.")
    public ResponseEntity<Map<String, Object>> getSelectedChildrensNmae(
            @ApiParam(name = "nomenclatureId", value = "id of nomenclature", required = true)
            @PathVariable(value = "nomenclatureId", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
            throws ResourceNotFoundException {
        return nomenclatureService.getSelectedChildrensName(nomenclatureId);
    }


    @GetMapping("/getNomenclatureNameAffectedForClient/{idClient}")
    @ApiOperation(value = "service to get parents name filtered")
    public List<String> getNomenclatureNameAffected(@ApiParam(name = "idClient", value = "id of client", required = true)
                                                    @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient) throws ResourceNotFoundException {
        return nomenclatureService.getNomenclatureNameAffectedForClient(idClient);
    }

    @GetMapping("/getNomenclatureNameAffectedForFrs/{idFrs}")
    @ApiOperation(value = "service to get parents name filtered")
    public List<String> getNomenclatureNameAffectedForFrs(@ApiParam(name = "idFrs", value = "id of client", required = true)
                                                          @PathVariable(value = "idFrs", required = true) @NotEmpty(message = "{http.error.0001}") String idFrs) throws ResourceNotFoundException {
        return nomenclatureService.getNomenclatureNameAffectedForFrs(idFrs);
    }


    @GetMapping("/getNomenclaturesName")
    @ApiOperation(value = "service to get name of nomenclatures")
    public List<String> getNomenclaturesName() {
        return nomenclatureService.getNomenclaturesName();
    }

    @GetMapping("/getNomenClaturesNameClient")
    @ApiOperation(value = "service to get name of nomenclatures")
    public List<String> getNomenClaturesNameClient() {
        return nomenclatureService.getNomenClaturesNameClient();
    }

    @GetMapping("/getNomenclaturesNameFrs")
    @ApiOperation(value = "service to get name of nomenclatures")
    public List<String> getNomenclaturesNameFrs() {
        return nomenclatureService.getNomenclaturesNameFrs();
    }


    @GetMapping("/getTargetEtapesNomenclature/{idNomenclature}")
    @ApiOperation(value = "service to get id nomenclature .")
    public List<EtapeProduction> getTargetEtapesNomenclature(
            @ApiParam(name = "idNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature)
            throws ResourceNotFoundException {
        return nomenclatureService.getTargetEtapesNomenclature(idNomenclature);
    }

}
