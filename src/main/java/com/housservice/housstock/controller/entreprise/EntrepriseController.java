package com.housservice.housstock.controller.entreprise;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.EntrepriseDto;
import com.housservice.housstock.service.EntrepriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Entreprises Management"})
public class EntrepriseController {
	
	private EntrepriseService entrepriseService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public EntrepriseController(EntrepriseService entrepriseService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.entrepriseService = entrepriseService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
    
    @GetMapping("/entreprise")
	 public List< EntrepriseDto > getAllEntreprise() {
		 		
		 return entrepriseService.getAllEntreprise();
		 	 
	 }

      @GetMapping("/entreprise/{id}")
	  @ApiOperation(value = "service to get one Entreprise by Id.")
	  public ResponseEntity < EntrepriseDto > getEntrepriseById(
			  @ApiParam(name = "id", value="id of entreprise", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String entrepriseId)
	  throws ResourceNotFoundException {
   	EntrepriseDto entreprise = entrepriseService.getEntrepriseById(entrepriseId);
		  if (entreprise == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(entreprise);
	  }
   
   @PutMapping("/entreprise")
	  public ResponseEntity<String> createEntreprise(@Valid @RequestBody EntrepriseDto entrepriseDto) {

   	  entrepriseService.createNewEntreprise(entrepriseDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
   
   @PutMapping("/entreprise/{id}")
	  public ResponseEntity <String> updateEntreprise(
			  @ApiParam(name = "id", value="id of entreprise", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String entrepriseId,
	          @Valid @RequestBody(required = true) EntrepriseDto entrepriseDto) throws ResourceNotFoundException {
		  
   	  entrepriseService.updateEntreprise(entrepriseDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

   
	  @DeleteMapping("/entreprise/{id}")
	  @ApiOperation(value = "service to delete one Entreprise by Id.")
	  public Map < String, Boolean > deleteEntreprise(
			  @ApiParam(name = "id", value="id of entreprise", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String entrepriseId) {

		  entrepriseService.deleteEntreprise(entrepriseId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
