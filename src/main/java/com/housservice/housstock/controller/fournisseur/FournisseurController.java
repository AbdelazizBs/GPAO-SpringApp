package com.housservice.housstock.controller.fournisseur;

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
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.service.FournisseurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Fournisseurs Management"})
public class FournisseurController {
	
	private FournisseurService fournisseurService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public FournisseurController(FournisseurService fournisseurService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.fournisseurService = fournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/fournisseur")
	 public List< FournisseurDto > getAllFournisseur() {
		 		
		 return fournisseurService.getAllFournisseur();
		 	 
	 }

    
    @GetMapping("/fournisseur/{id}")
	  @ApiOperation(value = "service to get one Fournisseur by Id.")
	  public ResponseEntity < FournisseurDto > getFournisseurById(
			  @ApiParam(name = "id", value="id of fournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
	  throws ResourceNotFoundException {
    	FournisseurDto fournisseur = fournisseurService.getFournisseurById(fournisseurId);
		  if (fournisseur == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(fournisseur);
	  }
    
    @PutMapping("/fournisseur")
	  public ResponseEntity<String> createFournisseur(@Valid @RequestBody FournisseurDto fournisseurDto) {
		  
    	  fournisseurService.createNewFournisseur(fournisseurDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/fournisseur/{id}")
	  public ResponseEntity <String> updateFournisseur(
			  @ApiParam(name = "id", value="id of fournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String fournisseurId,
	          @Valid @RequestBody(required = true) FournisseurDto fournisseurDto) throws ResourceNotFoundException {
		  
    	  fournisseurService.updateFournisseur(fournisseurDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/fournisseur/{id}")
	  @ApiOperation(value = "service to delete one Fournisseur by Id.")
	  public Map < String, Boolean > deleteFournisseur(
			  @ApiParam(name = "id", value="id of fournisseur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId) {

		  fournisseurService.deleteFournisseur(fournisseurId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
