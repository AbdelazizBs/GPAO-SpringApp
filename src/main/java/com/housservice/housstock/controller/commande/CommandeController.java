package com.housservice.housstock.controller.commande;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.service.CommandeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/getAllCommande")
    @ApiOperation(value = "service to get tout les Commandes ")
    public ResponseEntity<Map<String, Object>> getAllCommande(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return commandeService.getAllCommande(page,size);
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
    @DeleteMapping("/deleteCommande/{id}")
    @ApiOperation(value = "service to delete one Commande by Id.")
    public Map < String, Boolean > deletecommande(
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
    @PutMapping(value = "/addCommande")
    public ResponseEntity<String> createNewCommande(
            @RequestParam("fournisseur")
            @NotEmpty
            String fournisseur,
            @RequestParam("numBcd")
            @NotEmpty
            String numBcd,
            @RequestParam("commentaire")
            String commentaire,
            @RequestParam("dateCommande")
            @NotEmpty
            String dateCommande

    ) throws ResourceNotFoundException {
        System.out.println(fournisseur);
        commandeService.createNewCommande(fournisseur,numBcd,dateCommande,commentaire);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateCommande/{id}")
    public ResponseEntity <String> updateCommande(
            @ApiParam(name = "id", value="id", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String id,
            @RequestParam("commentaire")
            String commentaire,
            @RequestParam("numBcd")
            String numBcd,
            @RequestParam("fournisseur")
            String fournisseur,
            @RequestParam("dateCommande")
            String dateCommande

    ) throws ResourceNotFoundException {
        commandeService.UpdateCommande(dateCommande,commentaire,numBcd,fournisseur,id);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
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


    @GetMapping("/report/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String id){

        return commandeService.RecordReport(id);

    }
}
