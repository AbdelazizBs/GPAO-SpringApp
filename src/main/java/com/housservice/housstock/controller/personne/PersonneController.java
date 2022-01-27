package com.housservice.housstock.controller.personne;

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
import com.housservice.housstock.model.dto.PersonneDto;
import com.housservice.housstock.service.PersonneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Personnes Management"})
public class PersonneController {
	
	private PersonneService PersonneService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    @Autowired
	  public PersonneController(PersonneService PersonneService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.PersonneService = PersonneService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/Personne")
	 public List< PersonneDto > getAllPersonne() {
		 		
		 return PersonneService.getAllPersonne();
		 	 
	 }

      @GetMapping("/Personne/{id}")
	  @ApiOperation(value = "service to get one Personne by Id.")
	  public ResponseEntity < PersonneDto > getPersonneById(
			  @ApiParam(name = "id", value="id of Personne", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String PersonneId)
	  throws ResourceNotFoundException {
    	PersonneDto Personne = PersonneService.getPersonneById(PersonneId);
		  if (Personne == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(Personne);
	  }
    
      @PutMapping("/Personne")
	  public ResponseEntity<String> createPersonne(@Valid @RequestBody PersonneDto PersonneDto) {
		  
    	  PersonneService.createNewPersonne(PersonneDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
      @PutMapping("/Personne/{id}")
	  public ResponseEntity <String> updatePersonne(
			  @ApiParam(name = "id", value="id of Personne", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String PersonneId,
	          @Valid @RequestBody(required = true) PersonneDto PersonneDto) throws ResourceNotFoundException {
		  
    	  PersonneService.updatePersonne(PersonneDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/Personne/{id}")
	  @ApiOperation(value = "service to delete one Personne by Id.")
	  public Map < String, Boolean > deletePersonne(
			  @ApiParam(name = "id", value="id of Personne", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String PersonneId) {

		  PersonneService.deletePersonne(PersonneId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
