package com.housservice.housstock.controller.utilisateur;

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
import com.housservice.housstock.model.dto.UtilisateurDto;
import com.housservice.housstock.service.UtilisateurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Utilisateurs Management"})
public class UtilisateurController {
	
	private UtilisateurService utilisateurService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    @Autowired
	  public UtilisateurController(UtilisateurService utilisateurService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.utilisateurService = utilisateurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/utilisateur")
	 public List< UtilisateurDto > getAllUtilisateur() {
		 		
		 return utilisateurService.getAllUtilisateur();
		 	 
	 }

      @GetMapping("/utilisateur/{id}")
	  @ApiOperation(value = "service to get one Utilisateur by Id.")
	  public ResponseEntity < UtilisateurDto > getUtilisateurById(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId)
	  throws ResourceNotFoundException {
    	UtilisateurDto utilisateur = utilisateurService.getUtilisateurById(utilisateurId);
		  if (utilisateur == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(utilisateur);
	  }
    
      @PutMapping("/utilisateur")
	  public ResponseEntity<String> createUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto) {
		  
    	  utilisateurService.createNewUtilisateur(utilisateurDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
      @PutMapping("/utilisateur/{id}")
	  public ResponseEntity <String> updateUtilisateur(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String utilisateurId,
	          @Valid @RequestBody(required = true) UtilisateurDto utilisateurDto) throws ResourceNotFoundException {
		  
    	  utilisateurService.updateUtilisateur(utilisateurDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/utilisateur/{id}")
	  @ApiOperation(value = "service to delete one Utilisateur by Id.")
	  public Map < String, Boolean > deleteUtilisateur(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

		  utilisateurService.deleteUtilisateur(utilisateurId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	
	 
}
 