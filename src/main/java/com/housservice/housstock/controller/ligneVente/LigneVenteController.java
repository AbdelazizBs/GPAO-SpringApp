package com.housservice.housstock.controller.ligneVente;

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
import com.housservice.housstock.model.dto.LigneVenteDto;
import com.housservice.housstock.service.LigneVenteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"LigneVentes Management"})
public class LigneVenteController {
	
	private LigneVenteService ligneVenteService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public LigneVenteController(LigneVenteService ligneVenteService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.ligneVenteService = ligneVenteService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/ligneVente")
	 public List< LigneVenteDto > getAllLigneVente() {
		 		
		 return ligneVenteService.getAllLigneVente();
		 	 
	 }

    
    @GetMapping("/ligneVente/{id}")
	  @ApiOperation(value = "service to get one LigneVente by Id.")
	  public ResponseEntity < LigneVenteDto > getLigneVenteById(
			  @ApiParam(name = "id", value="id of ligneVente", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneVenteId)
	  throws ResourceNotFoundException {
    	LigneVenteDto ligneVente = ligneVenteService.getLigneVenteById(ligneVenteId);
		  if (ligneVente == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(ligneVente);
	  }
    
    @PutMapping("/ligneVente")
	  public ResponseEntity<String> createLigneVente(@Valid @RequestBody LigneVenteDto ligneVenteDto) {
		  
    	  ligneVenteService.createNewLigneVente(ligneVenteDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/ligneVente/{id}")
	  public ResponseEntity <String> updateLigneVente(
			  @ApiParam(name = "id", value="id of ligneVente", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String ligneVenteId,
	          @Valid @RequestBody(required = true) LigneVenteDto ligneVenteDto) throws ResourceNotFoundException {
		  
    	  ligneVenteService.updateLigneVente(ligneVenteDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/ligneVente/{id}")
	  @ApiOperation(value = "service to delete one LigneVente by Id.")
	  public Map < String, Boolean > deleteLigneVente(
			  @ApiParam(name = "id", value="id of ligneVente", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneVenteId) {

		  ligneVenteService.deleteLigneVente(ligneVenteId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
