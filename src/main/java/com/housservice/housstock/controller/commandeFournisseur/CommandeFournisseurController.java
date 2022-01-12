package com.housservice.housstock.controller.commandeFournisseur;

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
import com.housservice.housstock.model.dto.CommandeFournisseurDto;
import com.housservice.housstock.service.CommandeFournisseurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Commandes Fournisseurs Management"})
public class CommandeFournisseurController {

	private CommandeFournisseurService commandeFournisseurService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
		
    @Autowired
	  public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.commandeFournisseurService = commandeFournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
    
    @GetMapping("/commandeFournisseur")
		 public List< CommandeFournisseurDto > getAllCommandeFournisseur() {
			 		
			 return commandeFournisseurService.getAllCommandeFournisseur();
			 	 
		 }
    
    @GetMapping("/commandeFournisseur/{id}")
	  @ApiOperation(value = "service to get one Commande Fournisseur by Id.")
	  public ResponseEntity < CommandeFournisseurDto > getCommandeFournisseurById(
			  @ApiParam(name = "id", value="id of commandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeFournisseurId)
	  throws ResourceNotFoundException {
  	CommandeFournisseurDto commandeFournisseur = commandeFournisseurService.getCommandeFournisseurById(commandeFournisseurId);
		  if (commandeFournisseur == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(commandeFournisseur);
	  }
    
    @PutMapping("/commandeFournisseur")
	  public ResponseEntity<String> createCommandeFournisseur(@Valid @RequestBody CommandeFournisseurDto commandeFournisseurDto) {
		  
  	  commandeFournisseurService.createNewCommandeFournisseur(commandeFournisseurDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }

    
    @PutMapping("/commandeFournisseur/{id}")
	  public ResponseEntity <String> updateCommandeFournisseur(
			  @ApiParam(name = "id", value="id of commandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String commandeFournisseurId,
	          @Valid @RequestBody(required = true) CommandeFournisseurDto commandeFournisseurDto) throws ResourceNotFoundException {
		  
  	  commandeFournisseurService.updateCommandeFournisseur(commandeFournisseurDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
    
    
    @DeleteMapping("/commandeFournisseur/{id}")
	  @ApiOperation(value = "service to delete one Commande Fournisseur by Id.")
	  public Map < String, Boolean > deleteCommandeFournisseur(
			  @ApiParam(name = "id", value="id of commandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeFournisseurId) {

		  commandeFournisseurService.deleteCommandeFournisseur(commandeFournisseurId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	
}
