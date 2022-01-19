package com.housservice.housstock.controller.roles;

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
import com.housservice.housstock.model.dto.RolesDto;
import com.housservice.housstock.service.RolesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Roles Management"})
public class RolesController {
	
	private RolesService rolesService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public RolesController(RolesService rolesService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.rolesService = rolesService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/roles")
	 public List< RolesDto > getAllRoles() {
		 		
		 return rolesService.getAllRoles();
		 	 
	 }
  
    @GetMapping("/roles/{id}")
	@ApiOperation(value = "service to get one Roles by Id.")
	  public ResponseEntity < RolesDto > getRolesById(
			  @ApiParam(name = "id", value="id of roles", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String rolesId)
	  throws ResourceNotFoundException {
    	RolesDto roles = rolesService.getRolesById(rolesId);
		  if (roles == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(roles);
	  }
    
    @PutMapping("/roles")
	  public ResponseEntity<String> createRoles(@Valid @RequestBody RolesDto rolesDto) {
		  
    	  rolesService.createNewRoles(rolesDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/roles/{id}")
	  public ResponseEntity <String> updateRoles(
			  @ApiParam(name = "id", value="id of roles", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String rolesId,
	          @Valid @RequestBody(required = true) RolesDto rolesDto) throws ResourceNotFoundException {
		  
    	  rolesService.updateRoles(rolesDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/roles/{id}")
	  @ApiOperation(value = "service to delete one Roles by Id.")
	  public Map < String, Boolean > deleteRoles(
			  @ApiParam(name = "id", value="id of roles", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String rolesId) {

		  rolesService.deleteRoles(rolesId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
