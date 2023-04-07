package com.housservice.housstock.controller.client;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.service.ClientService;
import com.housservice.housstock.service.PictureService;
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
@RequestMapping("/api/v1/client")
@Api(tags = {"Clients Management"})
@Validated
public class ClientController {


    private final ClientService clientService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final PictureService pictureService;

    @Autowired
    public ClientController(ClientService clientService, MessageHttpErrorProperties messageHttpErrorProperties,
                            PictureService pictureService) {
        this.clientService = clientService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.pictureService = pictureService;
    }

    @GetMapping("/getAllClient")
    @ApiOperation(value = "service to get tout les clients ")
    public ResponseEntity<Map<String, Object>> getActiveClient(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return clientService.getActiveClient(page, size);

    }


    @GetMapping("/client/{id}")
    @ApiOperation(value = "service to get one Client by Id.")
    public ResponseEntity<Client> getClientById(
            @ApiParam(name = "id", value = "id of client", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String clientId) throws ResourceNotFoundException {
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), clientId)));
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/getClientsNameByIds/{nomenclatureId}")
    @ApiOperation(value = "service to get names Client by Id nomenclature.")
    public ResponseEntity<Map<String, Object>> getClientsNameByIds(
            @ApiParam(name = "nomenclatureId", value = "id of nomenclature", required = true)
            @PathVariable(value = "nomenclatureId", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
            throws ResourceNotFoundException {
        return clientService.getClientsNameByIds(nomenclatureId);
    }

    @GetMapping("/getClientByNameNomenclatures/{nameNomenclature}")
    @ApiOperation(value = "service to get names Client by Id nomenclature.")
    public ResponseEntity<Map<String, Object>> getClientByNameNomenclatures(
            @ApiParam(name = "nameNomenclature", value = "id of nomenclature", required = true)
            @PathVariable(value = "nameNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String nameNomenclature)
            throws ResourceNotFoundException {
        return clientService.getClientByNameNomenclatures(nameNomenclature);
    }

    @GetMapping("/getIdClients/{raisonSociale}")
    @ApiOperation(value = "service to get Id Client by raisonSociale.")

    public ResponseEntity<Map<String, Object>> getIdClients(@ApiParam(name = "raisonSociale", value = "raisonSociale of clients", required = true)
                                                            @PathVariable(value = "raisonSociale", required = true) @NotEmpty(message = "{http.error.0001}") String raisonSociale) throws ResourceNotFoundException {
        return clientService.getIdClients(raisonSociale);
    }

    @GetMapping("/getRaisonSociales")
    @ApiOperation(value = "service to get one Raison Sociale")
    public List<String> getRaisonSociales() {
        return clientService.getRaisonSociales();
    }


    @PutMapping(value = "/affecteNomEnClatureToClient/{idClient}")
    public ResponseEntity<String> affecteNomEnClatureToClient(
            @ApiParam(name = "idClient", value = "id of client ", required = true)
            @PathVariable(value = "idClient",
                    required = true) @NotEmpty(message = "{http.error.0001}") String idClient,
            @RequestParam("selectedOptions")
            List<String> selectedOptions
    ) throws ResourceNotFoundException {

        clientService.affecteNomEnClatureToClient(idClient, selectedOptions);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }


    @GetMapping("/getAllClientNonActive")
    public ResponseEntity<Map<String, Object>> getAllClientNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        return clientService.getClientNotActive(page, size);

    }


    @GetMapping("/search")
    @ApiOperation(value = "service to filter clients ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return clientService.search(textToFind, page, size, enVeille);

    }


    @GetMapping("/getNomenclaturesParClient/{raison}")
    @ApiOperation(value = "service to get nomenclatures by raison.")
    public ResponseEntity<Map<String, Object>> getArticles(
            @ApiParam(name = "raison", value = "raison of client", required = true)
            @PathVariable(value = "raison", required = true) @NotEmpty(message = "{http.error.0001}") String raison)
            throws ResourceNotFoundException {
        return clientService.getNomenclaturesParClient(raison);

    }


    @GetMapping("/getArticlesByRaisons/{raisonS}")
    @ApiOperation(value = "service to get one Client by Id.")
    public List<Article> getArticlesByRaisons(
            @ApiParam(name = "raisonS", value = "raison sociale of client", required = true)
            @PathVariable(value = "raisonS", required = true) @NotEmpty(message = "{http.error.0001}") String raisonS)
            throws ResourceNotFoundException {
        return clientService.getArticlesByRaisons(raisonS);
    }


    @DeleteMapping("/deleteSelectedClient/{idClientsSelected}")
    @ApiOperation(value = "service to delete many Personnel by Id.")
    public Map<String, Boolean> deleteClientSelected(
            @ApiParam(name = "idClientsSelected", value = "ids of client Selected", required = true)
            @PathVariable(value = "idClientsSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idClientsSelected) {
        clientService.deleteClientSelected(idClientsSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(value = "/addClient")
    public ResponseEntity<String> createNewClient(
            @RequestParam("refClientIris")
            @NotEmpty
            String refClientIris,
            @RequestParam("raisonSociale")
            @NotEmpty
            String raisonSociale,
            @RequestParam("adresse")
            @NotEmpty
            String adresse,
            @RequestParam("codePostal")
            @NotEmpty
            String codePostal,
            @RequestParam("ville")
            @NotEmpty
            String ville,
            @RequestParam("pays")
            @NotEmpty
            String pays,
            @RequestParam("region")
            @NotEmpty
            String region,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("statut") String statut,
            @RequestParam("brancheActivite") String brancheActivite,
            @RequestParam("secteurActivite") String secteurActivite,
            @RequestParam("incoterm") String incoterm,
            @RequestParam("echeance") String echeance,
            @RequestParam("modePaiement") String modePaiement,
            @RequestParam("nomBanque") String nomBanque,
            @RequestParam("adresseBanque") String adresseBanque,
            @RequestParam("codeDouane") String codeDouane,
            @RequestParam("rne") String rne,
            @RequestParam("cif") String cif,
            @RequestParam("telecopie") String telecopie,
            @RequestParam("rib") String rib,
            @RequestParam("swift") String swift,
            @RequestParam("images") MultipartFile[] images

    ) throws ResourceNotFoundException {

        clientService.createNewClient(refClientIris, raisonSociale, adresse, codePostal, ville, pays, region, phone, email, statut, brancheActivite, secteurActivite, incoterm
                , echeance, modePaiement, nomBanque, adresseBanque, codeDouane, rne, cif, telecopie, rib, swift, images);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }


    @PutMapping("/updateClient/{idClient}")
    public ResponseEntity<String> updateClient(
            @ApiParam(name = "idClient", value = "id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient,
            @RequestParam("refClientIris") String refClientIris,
            @RequestParam("raisonSociale") String raisonSociale,
            @RequestParam("adresse") String adresse,
            @RequestParam("codePostal") String codePostal,
            @RequestParam("ville") String ville,
            @RequestParam("pays") String pays,
            @RequestParam("region") String region,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("statut") String statut,
            @RequestParam("brancheActivite") String brancheActivite,
            @RequestParam("secteurActivite") String secteurActivite,
            @RequestParam("incoterm") String incoterm,
            @RequestParam("echeance") String echeance,
            @RequestParam("modePaiement") String modePaiement,
            @RequestParam("nomBanque") String nomBanque,
            @RequestParam("adresseBanque") String adresseBanque,
            @RequestParam("codeDouane") String codeDouane,
            @RequestParam("rne") String rne,
            @RequestParam("cif") String cif,
            @RequestParam("telecopie") String telecopie,
            @RequestParam("rib") String rib,
            @RequestParam("swift") String swift,
            @RequestParam("images") MultipartFile[] images) throws ResourceNotFoundException {

        clientService.updateClient(idClient, refClientIris, raisonSociale, adresse, codePostal, ville, pays, region, phone, email, statut, brancheActivite, secteurActivite, incoterm
                , echeance, modePaiement, nomBanque, adresseBanque, codeDouane, rne, cif, telecopie, rib, swift, images);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @GetMapping("/onSortActiveClient")
    @ApiOperation(value = "service to get get All active client   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveClient(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size,
                                                                  @RequestParam(defaultValue = "field") String field,
                                                                  @RequestParam(defaultValue = "order") String order) {
        return clientService.onSortActiveClient(page, size, field, order);

    }

    @GetMapping("/onSortClientNotActive")
    @ApiOperation(value = "service to get get All client not active sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortClientNotActive(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "field") String field,
                                                                     @RequestParam(defaultValue = "order") String order) {
        return clientService.onSortClientNotActive(page, size, field, order);

    }


    @PutMapping("/miseEnVeille/{idClient}")
    public ResponseEntity<String> miseEnVeille(
            @ApiParam(name = "idClient", value = "id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient,
            @RequestBody(required = true) ClientDto clientDto) throws ResourceNotFoundException {

        clientService.miseEnVeille(idClient);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @PutMapping("/addContactClient/{idClient}")
    public ResponseEntity<String> addContactClient(
            @ApiParam(name = "idClient", value = "id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient,
            @Valid @RequestBody(required = true) ContactDto contactDto) throws ResourceNotFoundException {
        clientService.addContactClient(contactDto, idClient);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @PutMapping("/updateContactClient/{idContact}")
    public ResponseEntity<String> updateContactClient(
            @ApiParam(name = "idContact", value = "id of contact", required = true)
            @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact,
            @Valid @RequestBody(required = true) ContactDto ContactDto) throws ResourceNotFoundException {
        clientService.updateContactClient(ContactDto, idContact);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @DeleteMapping("/deleteClient/{id}")
    @ApiOperation(value = "service to delete one Client by Id.")
    public Map<String, Boolean> deleteclient(
            @ApiParam(name = "id", value = "id of client", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String clientId)
            throws ResourceNotFoundException {
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), clientId)));

        clientService.deleteClient(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @DeleteMapping("/deleteContactClient/{idContact}")
    @ApiOperation(value = "service to delete one Client by Id.")
    public Map<String, Boolean> deleteContactClient(
            @ApiParam(name = "idContact", value = "id of client", required = true)
            @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
            throws ResourceNotFoundException {
        clientService.deleteContactClient(idContact);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/removePictures/{idClient}")
    @ApiOperation(value = "service to delete all  Picture by idClient and idPicture.")
    public Map<String, Boolean> removePictures(
            @ApiParam(name = "idClient", value = "id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient)
            throws ResourceNotFoundException {
        clientService.removePictures(idClient);
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
        clientService.removePicture(idPicture);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
