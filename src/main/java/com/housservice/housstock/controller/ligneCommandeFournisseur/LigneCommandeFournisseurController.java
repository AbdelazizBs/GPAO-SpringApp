package com.housservice.housstock.controller.ligneCommandeFournisseur;

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
import com.housservice.housstock.model.dto.LigneCommandeFournisseurDto;
import com.housservice.housstock.service.LigneCommandeFournisseurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Ligne Commande Fournisseur Management"})
public class LigneCommandeFournisseurController {
	
	private LigneCommandeFournisseurService ligneCommandeFournisseurService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public LigneCommandeFournisseurController(LigneCommandeFournisseurService ligneCommandeFournisseurService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.ligneCommandeFournisseurService = ligneCommandeFournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/ligneCommandeFournisseur")
	 public List< LigneCommandeFournisseurDto > getAllLigneCommandeFournisseur() {
		 		
		 return ligneCommandeFournisseurService.getAllLigneCommandeFournisseur();
		 	 
	 }

    
    @GetMapping("/ligneCommandeFournisseur/{id}")
	  @ApiOperation(value = "service to get one LigneCommandeFournisseur by Id.")
	  public ResponseEntity < LigneCommandeFournisseurDto > getLigneCommandeFournisseurById(
			  @ApiParam(name = "id", value="id of ligneCommandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeFournisseurId)
	  throws ResourceNotFoundException {
    	LigneCommandeFournisseurDto ligneCommandeFournisseur = ligneCommandeFournisseurService.getLigneCommandeFournisseurById(ligneCommandeFournisseurId);
		  if (ligneCommandeFournisseur == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(ligneCommandeFournisseur);
	  }
    
    @PutMapping("/ligneCommandeFournisseur")
	  public ResponseEntity<String> createLigneCommandeFournisseur(@Valid @RequestBody LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
		  
    	  ligneCommandeFournisseurService.createNewLigneCommandeFournisseur(ligneCommandeFournisseurDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/ligneCommandeFournisseur/{id}")
	  public ResponseEntity <String> updateLigneCommandeFournisseur(
			  @ApiParam(name = "id", value="id of ligneCommandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String ligneCommandeFournisseurId,
	          @Valid @RequestBody(required = true) LigneCommandeFournisseurDto ligneCommandeFournisseurDto) throws ResourceNotFoundException {
		  
    	  ligneCommandeFournisseurService.updateLigneCommandeFournisseur(ligneCommandeFournisseurDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/ligneCommandeFournisseur/{id}")
	  @ApiOperation(value = "service to delete one LigneCommandeFournisseur by Id.")
	  public Map < String, Boolean > deleteLigneCommandeFournisseur(
			  @ApiParam(name = "id", value="id of ligneCommandeFournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeFournisseurId) {

		  ligneCommandeFournisseurService.deleteLigneCommandeFournisseur(ligneCommandeFournisseurId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
