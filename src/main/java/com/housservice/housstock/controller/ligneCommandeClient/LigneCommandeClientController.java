package com.housservice.housstock.controller.ligneCommandeClient;

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
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import com.housservice.housstock.service.LigneCommandeClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Ligne Commande Client Management"})
public class LigneCommandeClientController {
	
	private LigneCommandeClientService ligneCommandeClientService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public LigneCommandeClientController(LigneCommandeClientService ligneCommandeClientService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.ligneCommandeClientService = ligneCommandeClientService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/ligneCommandeClient")
	 public List< LigneCommandeClientDto > getAllLigneCommandeClient() {
		 		
		 return ligneCommandeClientService.getAllLigneCommandeClient();
		 	 
	 }

    
    @GetMapping("/ligneCommandeClient/{id}")
	  @ApiOperation(value = "service to get one LigneCommandeClient by Id.")
	  public ResponseEntity < LigneCommandeClientDto > getLigneCommandeClientById(
			  @ApiParam(name = "id", value="id of ligneCommandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeClientId)
	  throws ResourceNotFoundException {
    	LigneCommandeClientDto ligneCommandeClient = ligneCommandeClientService.getLigneCommandeClientById(ligneCommandeClientId);
		  if (ligneCommandeClient == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(ligneCommandeClient);
	  }
    
    @PutMapping("/ligneCommandeClient")
	  public ResponseEntity<String> createLigneCommandeClient(@Valid @RequestBody LigneCommandeClientDto ligneCommandeClientDto) {
		  
    	  ligneCommandeClientService.createNewLigneCommandeClient(ligneCommandeClientDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/ligneCommandeClient/{id}")
	  public ResponseEntity <String> updateLigneCommandeClient(
			  @ApiParam(name = "id", value="id of ligneCommandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String ligneCommandeClientId,
	          @Valid @RequestBody(required = true) LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {
		  
    	  ligneCommandeClientService.updateLigneCommandeClient(ligneCommandeClientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/ligneCommandeClient/{id}")
	  @ApiOperation(value = "service to delete one LigneCommandeClient by Id.")
	  public Map < String, Boolean > deleteLigneCommandeClient(
			  @ApiParam(name = "id", value="id of ligneCommandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeClientId) {

		  ligneCommandeClientService.deleteLigneCommandeClient(ligneCommandeClientId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
  

}