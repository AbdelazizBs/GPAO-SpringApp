package com.housservice.housstock.controller.fournisseur;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.service.FournisseurService;
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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/fournisseur")
@Api(tags = {"fournisseurs Management"})
@Validated
public class FournisseurController {

    private final FournisseurService fournisseurService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final FournisseurRepository fournisseurRepository;

    @Autowired
    public FournisseurController(FournisseurService fournisseurService, MessageHttpErrorProperties messageHttpErrorProperties,
                                 FournisseurRepository fournisseurRepository) {

        this.fournisseurService = fournisseurService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;

        this.fournisseurRepository = fournisseurRepository;
    }

    @GetMapping("/getAllFournisseur")
    @ApiOperation(value = "service to get tout les fournisseurs ")
    public ResponseEntity<Map<String, Object>> getAllFournisseur(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return fournisseurService.findFournisseurActif(page, size);

    }


    @GetMapping("/fournisseur/{id}")
    @ApiOperation(value = "service to get one Fournisseur by Id.")
    public ResponseEntity<Fournisseur> getFournisseurById(
            @ApiParam(name = "id", value = "id of fournisseur", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
            throws ResourceNotFoundException {

        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurId)));
        return ResponseEntity.ok().body(fournisseur);
    }

    @GetMapping("/getFournisseursNameById/{idNomenclature}")
    @ApiOperation(value = "service to get one Fournisseurs Intitule by Id nomenclature.")
    public ResponseEntity<Map<String, Object>> getFournisseursNameById(
            @ApiParam(name = "idNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature)
            throws ResourceNotFoundException {
        return fournisseurService.getFournisseursNameById(idNomenclature);
    }

    @GetMapping("/getIdFournisseurs/{intitule}")
    @ApiOperation(value = "service to get Id Fournisseur by intitule.")
    public ResponseEntity<Map<String, Object>> getIdFournisseurs(@ApiParam(name = "intitule", value = "intitule of fournisseurs", required = true)
                                                                 @PathVariable(value = "intitule", required = true) @NotEmpty(message = "{http.error.0001}") String intitule) throws ResourceNotFoundException {
        return fournisseurService.getIdFournisseurs(intitule);
    }

    @GetMapping("/getIntitules")
    @ApiOperation(value = "service to get one Intitule")
    public List<String> getIntitules() {
        return fournisseurService.getIntitules();
    }


    @GetMapping("/getAllFournisseurNonActive")
    public ResponseEntity<Map<String, Object>> getAllFournisseurNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return fournisseurService.findFournisseurNonActive(page, size);

    }


    @GetMapping("/search")
    @ApiOperation(value = "service to filter fournisseurs ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return fournisseurService.search(textToFind, page, size, enVeille);

    }

    @PutMapping(value = "/affecteNomEnClatureToFournisseur/{idFournisseur}")
    public ResponseEntity<String> affecteNomEnClatureToFournisseur(
            @ApiParam(name = "idFournisseur", value = "id of fournisseur", required = true)
            @PathVariable(value = "idFournisseur",
                    required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur,
            @RequestParam("selectedOptions")
            List<String> selectedOptions
    ) throws ResourceNotFoundException {

        fournisseurService.affecteNomEnClatureToFournisseur(idFournisseur, selectedOptions);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @GetMapping("/getFrsByNameNomenclatures/{nameNomenclature}")
    @ApiOperation(value = "service to get names Client by Id nomenclature.")
    public ResponseEntity<Map<String, Object>> getFrsByNameNomenclatures(
            @ApiParam(name = "nameNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "nameNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String nameNomenclature)
            throws ResourceNotFoundException {
        return fournisseurService.getFrsByNameNomenclatures(nameNomenclature);
    }

    @DeleteMapping("/deleteSelectedFournisseur/{idFournisseursSelected}")
    @ApiOperation(value = "service to delete many Fournisseur by Id.")
    public Map<String, Boolean> deleteFournisseurSelected(
            @ApiParam(name = "idFournisseursSelected", value = "ids of fournisseur Selected", required = true) @PathVariable(value = "idFournisseursSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idFournisseursSelected) {
        fournisseurService.deleteFournisseurSelected(idFournisseursSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(value = "/addFournisseur")
    public ResponseEntity<String> createNewFournisseur(
            @RequestParam("refFrsIris")
            @NotEmpty
            String refFrsIris,

            @RequestParam("intitule")
            @NotEmpty
            String intitule,

            @RequestParam("abrege")
            String abrege,

            @RequestParam("statut")
            String statut,

            @RequestParam("adresse")
            @NotEmpty
            String adresse,

            @RequestParam("codePostal")
            @NotEmpty
            String codePostal,

            @RequestParam("ville")
            @NotEmpty
            String ville,

            @RequestParam("region")
            @NotEmpty
            String region,

            @RequestParam("pays")
            @NotEmpty
            String pays,

            @RequestParam("telephone")
            String telephone,

            @RequestParam("telecopie")
            String telecopie,

            @RequestParam("linkedin")
            String linkedin,

            @RequestParam("email")
            String email,

            @RequestParam("siteWeb")
            String siteWeb,

            @RequestParam("nomBanque")
            String nomBanque,

            @RequestParam("adresseBanque")
            String adresseBanque,

            @RequestParam("rib")
            String rib,

            @RequestParam("swift")
            String swift,

            @RequestParam("codeDouane")
            String codeDouane,

            @RequestParam("rne")
            String rne,

            @RequestParam("identifiantTva")
            String identifiantTva,


            @RequestParam("images") MultipartFile[] images

    ) throws ResourceNotFoundException {

        fournisseurService.createNewFournisseur(refFrsIris, intitule, abrege, statut, adresse, codePostal, ville, region, pays, telephone, telecopie, linkedin, email, siteWeb, nomBanque, adresseBanque, rib, swift, codeDouane, rne, identifiantTva, images);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }


    @PutMapping("/updateFournisseur/{idFournisseur}")
    public ResponseEntity<String> updateFournisseur(
            @ApiParam(name = "idFournisseur", value = "id of Fournisseur", required = true)
            @PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur,
            @RequestParam("refFrsIris") String refFrsIris,
            @RequestParam("intitule") String intitule,
            @RequestParam("abrege") String abrege,
            @RequestParam("statut") String statut,
            @RequestParam("adresse") String adresse,
            @RequestParam("codePostal") String codePostal,
            @RequestParam("ville") String ville,
            @RequestParam("region") String region,
            @RequestParam("pays") String pays,
            @RequestParam("telephone") String telephone,
            @RequestParam("telecopie") String telecopie,
            @RequestParam("linkedin") String linkedin,
            @RequestParam("email") String email,
            @RequestParam("siteWeb") String siteWeb,
            @RequestParam("nomBanque") String nomBanque,
            @RequestParam("adresseBanque") String adresseBanque,
            @RequestParam("rib") String rib,
            @RequestParam("swift") String swift,
            @RequestParam("codeDouane") String codeDouane,
            @RequestParam("rne") String rne,
            @RequestParam("identifiantTva") String identifiantTva,

            @RequestParam("images") MultipartFile[] images) throws ResourceNotFoundException {

        fournisseurService.updateFournisseur(idFournisseur, refFrsIris, intitule, abrege, statut, adresse, codePostal, ville, region, pays, telephone, telecopie, linkedin, email, siteWeb, nomBanque, adresseBanque, rib, swift, codeDouane, rne, identifiantTva, images);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @PutMapping("/miseEnVeille/{idFournisseur}")
    public ResponseEntity<String> miseEnVeille(
            @ApiParam(name = "idFournisseur", value = "id of fournisseur", required = true)
            @PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur,
            @RequestBody(required = true) FournisseurDto fournisseurDto) throws ResourceNotFoundException {

        fournisseurService.miseEnVeille(idFournisseur);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @PutMapping("/addContactFournisseur/{idFournisseur}")
    public ResponseEntity<String> addContactFournisseur(
            @ApiParam(name = "idFournisseur", value = "id of fournisseur", required = true)
            @PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur,
            @Valid @RequestBody(required = true) ContactDto contactDto) throws ResourceNotFoundException {
        fournisseurService.addContactFournisseur(contactDto, idFournisseur);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @PutMapping("/updateContactFournisseur/{idContact}")
    public ResponseEntity<String> updateContactFournisseur(
            @ApiParam(name = "idContact", value = "id of contact", required = true)
            @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact,
            @Valid @RequestBody(required = true) ContactDto ContactDto) throws ResourceNotFoundException {
        fournisseurService.updateContactFournisseur(ContactDto, idContact);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @DeleteMapping("/deleteFournisseur/{id}")
    @ApiOperation(value = "service to delete one Fournisseur by Id.")
    public Map<String, Boolean> deleteclient(
            @ApiParam(name = "id", value = "id of fournisseur", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
            throws ResourceNotFoundException {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurId)));

        fournisseurService.deleteFournisseur(fournisseur);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @DeleteMapping("/deleteContactFournisseur/{idContact}")
    @ApiOperation(value = "service to delete one Fournisseur by Id.")
    public Map<String, Boolean> deleteContactFournisseur(
            @ApiParam(name = "idContact", value = "id of fournisseur", required = true)
            @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
            throws ResourceNotFoundException {
        fournisseurService.deleteContactFournisseur(idContact);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/removePictures/{idFournisseur}")
    @ApiOperation(value = "service to delete all  Picture by idFournisseur and idPicture.")
    public Map<String, Boolean> removePictures(
            @ApiParam(name = "idFournisseur", value = "id of fournisseur", required = true)
            @PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur)
            throws ResourceNotFoundException {
        fournisseurService.removePictures(idFournisseur);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/removePicture/{idPicture}")
    @ApiOperation(value = "service to delete one Picture by Id.")
    public Map<String, Boolean> removePicture(
            @ApiParam(name = "idPicture", value = "id of picture", required = true)
            @PathVariable(value = "idPicture", required = true) @NotEmpty(message = "{http.error.0001}") String idPicture)
            throws ResourceNotFoundException {
        fournisseurService.removePicture(idPicture);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
