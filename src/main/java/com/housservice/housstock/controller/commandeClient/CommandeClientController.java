package com.housservice.housstock.controller.commandeClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.service.CommandeClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Commandes Clients Management"})
public class CommandeClientController {

	private CommandeClientService commandeClientService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
		
    @Autowired
	  public CommandeClientController(CommandeClientService commandeClientService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.commandeClientService = commandeClientService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
    
    @GetMapping("/commandeClient")
		 public List< CommandeClientDto > getAllCommandeClient() {
			 		
			 return commandeClientService.getAllCommandeClient();
			 	 
		 }
    
    @GetMapping("/commandeClient/{id}")
	  @ApiOperation(value = "service to get one Commande Client by Id.")
	  public ResponseEntity < CommandeClientDto > getCommandeClientById(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeClientId)
	  throws ResourceNotFoundException {
  	CommandeClientDto commandeClient = commandeClientService.getCommandeClientById(commandeClientId);
		  if (commandeClient == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(commandeClient);
	  }
    
    @PutMapping("/commandeClient")
	  public ResponseEntity<String> createCommandeClient(@Valid @RequestBody CommandeClientDto commandeClientDto) {
		  
  	  commandeClientService.createNewCommandeClient(commandeClientDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }

    
    @PutMapping("/commandeClient/{id}")
	  public ResponseEntity <String> updateCommandeClient(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String commandeClientId,
	          @Valid @RequestBody(required = true) CommandeClientDto commandeClientDto) throws ResourceNotFoundException {
		  
  	  commandeClientService.updateCommandeClient(commandeClientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
    
    
    @DeleteMapping("/commandeClient/{id}")
	  @ApiOperation(value = "service to delete one Commande Client by Id.")
	  public Map < String, Boolean > deleteCommandeClient(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeClientId) {

		  commandeClientService.deleteCommandeClient(commandeClientId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
    
}
