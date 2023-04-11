package com.housservice.housstock.controller.CommandeClient;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.service.CommandeClientService;
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
@RequestMapping("/api/v1/commandeClient")
@Api(tags = {"CommandeClients Management"})
@Validated
public class CommandeClientController {
    private final CommandeClientService commandeClientService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public CommandeClientController(CommandeClientService commandeClientService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.commandeClientService = commandeClientService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }
    @GetMapping("/commandeClient/{id}")
    @ApiOperation(value = "service to get one CommandeClient by Id.")
    public ResponseEntity<CommandeClient> getCommandeClientById(
            @ApiParam(name = "id", value="id of CommandeClient", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientService.getCommandeClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        return ResponseEntity.ok().body(commandeClient);
    }
    @DeleteMapping("/deleteSelectedCommandeClient/{idCommandeClientsSelected}")
    @ApiOperation(value = "service to delete many CommandeClient by Id.")
    public Map<String, Boolean> deleteCommandeClientSelected(
            @ApiParam(name = "idCommandeClientsSelected", value = "ids of commandeClient Selected", required = true)
            @PathVariable(value = "idClientsSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idCommandeClientsSelected) {
        commandeClientService.deleteCommandeClientSelected(idCommandeClientsSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PutMapping(value = "/addCommandeClient")
    public ResponseEntity<String> createNewCommandeClient(
            @RequestParam("date")
            @NotEmpty
            String date,
            @RequestParam("commentaire")
            @NotEmpty
            String commentaire,
            @RequestParam("numBcd")
            @NotEmpty
            String numBcd,
            @RequestParam("client")
            @NotEmpty
            String client


    ) throws ResourceNotFoundException {
        commandeClientService.createNewCommandeClient(date,commentaire,numBcd,client);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateCommandeClient/{idCommandeClient}")
    public ResponseEntity <String> updateCommandeClient(
            @ApiParam(name = "idClient", value="id of client", required = true)
            @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idCommandeClient,
            @RequestParam("commentaire")
            @NotEmpty
            String commentaire,
            @RequestParam("date")
            @NotEmpty
            String date,
            @RequestParam("numBcd")
            @NotEmpty
            String numBcd,
            @RequestParam("client")
            @NotEmpty
            String client) throws ResourceNotFoundException {

        commandeClientService.UpdateCommandeClient(idCommandeClient,commentaire,numBcd,client,date);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteCommandeClient/{id}")
    @ApiOperation(value = "service to delete one CommandeClient by Id.")
    public Map < String, Boolean > deleteCommandeClient(
            @ApiParam(name = "id", value="id of commandeClient", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeClientId)
            throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientService.getCommandeClientById(commandeClientId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeClientId)));

        commandeClientService.deleteCommandeClient(commandeClient);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @GetMapping("/onSortActiveCommandeClient")
    @ApiOperation(value = "service to get get All active commandeClient   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveClient(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "3") int size,
                                                                       @RequestParam(defaultValue = "field") String field,
                                                                       @RequestParam(defaultValue = "order") String order){
        return commandeClientService.onSortActiveCommandeClient(page,size,field,order);

    }
    @GetMapping("/getIdCommandeClients/{numBcd}")
    @ApiOperation(value = "service to get Id CommandeClient by numBcd.")

    public ResponseEntity<Map<String, Object>>  getIdClients(  @ApiParam(name = "numBcd", value="numBcd of commandeClients", required = true)
                                                                    @PathVariable(value = "numBcd", required = true) @NotEmpty(message = "{http.error.0001}") String numBcd) throws ResourceNotFoundException {
        return commandeClientService.getIdCommandeClients(numBcd);
    }
    @GetMapping("/getallCommandeClient")
    public int getallCommandeClient(){
        return commandeClientService.getallCommandeClient();
    }
    @GetMapping("/getAllRefClients")
    @ApiOperation(value = "service to get one Reference client")
    public List<String> getAllRefClients() {
        return commandeClientService.getAllClients();
    }
    @GetMapping("/getAllCommandeClient")
    @ApiOperation(value = "service to get tout les CommandeClients ")
    public ResponseEntity<Map<String, Object>> getAllCommandeClient(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return commandeClientService.getAllCommandeClient(page,size);
    }
    @PutMapping("/addArticleCommandeClient/{idCommandeClient}")
    public ResponseEntity <String> addArticleCommandeClient(
            @ApiParam(name = "idCommandeClient", value="id of client", required = true)
            @PathVariable(value = "idCommandeClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idCommandeClient,
            @Valid @RequestBody(required = true) ArticleDto articleDto) throws ResourceNotFoundException {
        commandeClientService.addArticleCommandeClient(articleDto,idCommandeClient);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @PutMapping("/updateCommandeClientClient/{idArticle}")
    public ResponseEntity <String> updateArticleCommandeClient(
            @ApiParam(name = "idArticle", value="id of contact", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}")  String idArticle,
            @Valid @RequestBody(required = true) ArticleDto ArticleDto ) throws ResourceNotFoundException {
        commandeClientService.updateArticleCommandeClient(ArticleDto,idArticle);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteArticleCommandeClient/{idArticle}")
    @ApiOperation(value = "service to delete one CommandeClient by Id.")
    public Map< String, Boolean > deleteArticleCommandeClient(
            @ApiParam(name = "idArticle", value="id of commandeClient", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}") String idArticle)
            throws ResourceNotFoundException {
        commandeClientService.deleteArticleCommandeClient(idArticle);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
