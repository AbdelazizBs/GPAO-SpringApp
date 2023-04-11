package com.housservice.housstock.controller.commande;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.service.CommandeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/commande")
@Api(tags = {"Commandes Management"})
@Validated
public class CommandeController {
    private final CommandeService commandeService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public CommandeController(CommandeService commandeService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.commandeService = commandeService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }
    @GetMapping("/commande/{id}")
    @ApiOperation(value = "service to get one Commande by Id.")
    public ResponseEntity<Commande> getCommandeById(
            @ApiParam(name = "id", value="id of Commande", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        Commande commande = commandeService.getCommandeById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        return ResponseEntity.ok().body(commande);
    }
    @DeleteMapping("/deleteSelectedCommande/{idCommandesSelected}")
    @ApiOperation(value = "service to delete many Commande by Id.")
    public Map<String, Boolean> deleteCommandeSelected(
            @ApiParam(name = "idCommandesSelected", value = "ids of commande Selected", required = true)
            @PathVariable(value = "idFournisseursSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idCommandesSelected) {
        commandeService.deleteCommandeSelected(idCommandesSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PutMapping(value = "/addCommande")
    public ResponseEntity<String> createNewCommande(
            @RequestParam("date")
            @NotEmpty
            String date,
            @RequestParam("commentaire")
            @NotEmpty
            String commentaire,
            @RequestParam("numBcd")
            @NotEmpty
            String numBcd,
            @RequestParam("fournisseur")
            @NotEmpty
            String fournisseur


    ) throws ResourceNotFoundException {
        commandeService.createNewCommande(date,commentaire,numBcd,fournisseur);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateCommande/{idCommande}")
    public ResponseEntity <String> updateCommande(
            @ApiParam(name = "idFournisseur", value="id of fournisseur", required = true)
            @PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}")  String idCommande,
            @RequestParam("commentaire")
            @NotEmpty
            String commentaire,
            @RequestParam("date")
            @NotEmpty
            String date,
            @RequestParam("numBcd")
            @NotEmpty
            String numBcd,
            @RequestParam("fournisseur")
            @NotEmpty
            String fournisseur) throws ResourceNotFoundException {

        commandeService.UpdateCommande(idCommande,commentaire,numBcd,fournisseur,date);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteCommande/{id}")
    @ApiOperation(value = "service to delete one Commande by Id.")
    public Map < String, Boolean > deleteCommande(
            @ApiParam(name = "id", value="id of commande", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeId)
            throws ResourceNotFoundException {
        Commande commande = commandeService.getCommandeById(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeId)));

        commandeService.deleteCommande(commande);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @GetMapping("/onSortActiveCommande")
    @ApiOperation(value = "service to get get All active commande   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveFournisseur(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "3") int size,
                                                                       @RequestParam(defaultValue = "field") String field,
                                                                       @RequestParam(defaultValue = "order") String order){
        return commandeService.onSortActiveCommande(page,size,field,order);

    }
    @GetMapping("/getIdCommandes/{numBcd}")
    @ApiOperation(value = "service to get Id Commande by numBcd.")

    public ResponseEntity<Map<String, Object>>  getIdFournisseurs(  @ApiParam(name = "numBcd", value="numBcd of commandes", required = true)
                                                                    @PathVariable(value = "numBcd", required = true) @NotEmpty(message = "{http.error.0001}") String numBcd) throws ResourceNotFoundException {
        return commandeService.getIdCommandes(numBcd);
    }
    @GetMapping("/getallCommande")
    public int getallCommande(){
        return commandeService.getallCommande();
    }
    @GetMapping("/getAllRefFournisseurs")
    @ApiOperation(value = "service to get one Reference fournisseur")
    public List<String> getAllRefFournisseurs() {
        return commandeService.getAllFournisseurs();
    }
    @GetMapping("/getAllCommande")
    @ApiOperation(value = "service to get tout les Commandes ")
    public ResponseEntity<Map<String, Object>> getAllCommande(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return commandeService.getAllCommande(page,size);
    }
    @PutMapping("/addArticleCommande/{idCommande}")
    public ResponseEntity <String> addArticleCommande(
            @ApiParam(name = "idCommande", value="id of client", required = true)
            @PathVariable(value = "idCommande", required = true) @NotEmpty(message = "{http.error.0001}")  String idCommande,
            @Valid @RequestBody(required = true) ArticleDto articleDto) throws ResourceNotFoundException {
        commandeService.addArticleCommande(articleDto,idCommande);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @PutMapping("/updateCommandeClient/{idArticle}")
    public ResponseEntity <String> updateArticleCommande(
            @ApiParam(name = "idArticle", value="id of contact", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}")  String idArticle,
            @Valid @RequestBody(required = true) ArticleDto ArticleDto ) throws ResourceNotFoundException {
        commandeService.updateArticleCommande(ArticleDto,idArticle);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteArticleCommande/{idArticle}")
    @ApiOperation(value = "service to delete one Commande by Id.")
    public Map< String, Boolean > deleteArticleCommande(
            @ApiParam(name = "idArticle", value="id of commande", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}") String idArticle)
            throws ResourceNotFoundException {
        commandeService.deleteArticleCommande(idArticle);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @GetMapping("/getAllMatiere")
    @ApiOperation(value = "service to get one Reference fournisseur")
    public List<String> getAllMatiere() {
        return commandeService.getAllMatiere();
    }

    @PutMapping("/addMatiere/{designation}")
    public ResponseEntity <String> addMatiere(
            @ApiParam(name = "designation", value="designation", required = true)
            @PathVariable(value = "designation", required = true) @NotEmpty(message = "{http.error.0001}")  String designation) throws ResourceNotFoundException {
        commandeService.addMatiere(designation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }


}
