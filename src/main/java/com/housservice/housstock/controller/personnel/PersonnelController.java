package com.housservice.housstock.controller.personnel;

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
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.service.PersonnelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Personnels Management"})
public class PersonnelController {
	
	private PersonnelService PersonnelService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    @Autowired
	  public PersonnelController(PersonnelService PersonnelService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.PersonnelService = PersonnelService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/personnel")
	 public List< PersonnelDto > getAllPersonnel() {
		 		
		 return PersonnelService.getAllPersonnel();
		 	 
	 }

      @GetMapping("/personnel/{id}")
	  @ApiOperation(value = "service to get one Personnel by Id.")
	  public ResponseEntity < PersonnelDto > getPersonnelById(
			  @ApiParam(name = "id", value="id of Personnel", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String PersonnelId)
	  throws ResourceNotFoundException {
    	PersonnelDto Personnel = PersonnelService.getPersonnelById(PersonnelId);
		  if (Personnel == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(Personnel);
	  }
    
      @PutMapping("/personnel")
	  public ResponseEntity<String> createPersonnel(@Valid @RequestBody PersonnelDto PersonnelDto) {
		  
    	  PersonnelService.createNewPersonnel(PersonnelDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
      @PutMapping("/personnel/{id}")
	  public ResponseEntity <String> updatePersonnel(
			  @ApiParam(name = "id", value="id of Personnel", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String PersonnelId,
	          @Valid @RequestBody(required = true) PersonnelDto PersonnelDto) throws ResourceNotFoundException {
		  
    	  PersonnelService.updatePersonnel(PersonnelDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/personnel/{id}")
	  @ApiOperation(value = "service to delete one Personnel by Id.")
	  public Map < String, Boolean > deletePersonnel(
			  @ApiParam(name = "id", value="id of Personnel", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String PersonnelId) {

		  PersonnelService.deletePersonnel(PersonnelId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
