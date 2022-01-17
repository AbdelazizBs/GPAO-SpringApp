package com.housservice.housstock.controller.ventes;

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
import com.housservice.housstock.model.dto.VentesDto;
import com.housservice.housstock.service.VentesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Ventes Management"})
public class VentesController {
	
	private VentesService VentesService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public VentesController(VentesService VentesService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.VentesService = VentesService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/Ventes")
	 public List< VentesDto > getAllVentes() {
		 		
		 return VentesService.getAllVentes();
		 	 
	 }

    
    @GetMapping("/Ventes/{id}")
	  @ApiOperation(value = "service to get one Ventes by Id.")
	  public ResponseEntity < VentesDto > getVentesById(
			  @ApiParam(name = "id", value="id of Ventes", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String VentesId)
	  throws ResourceNotFoundException {
    	VentesDto Ventes = VentesService.getVentesById(VentesId);
		  if (Ventes == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(Ventes);
	  }
    
    @PutMapping("/Ventes")
	  public ResponseEntity<String> createVentes(@Valid @RequestBody VentesDto VentesDto) {
		  
    	  VentesService.createNewVentes(VentesDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/Ventes/{id}")
	  public ResponseEntity <String> updateVentes(
			  @ApiParam(name = "id", value="id of Ventes", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String VentesId,
	          @Valid @RequestBody(required = true) VentesDto VentesDto) throws ResourceNotFoundException {
		  
    	  VentesService.updateVentes(VentesDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/Ventes/{id}")
	  @ApiOperation(value = "service to delete one Ventes by Id.")
	  public Map < String, Boolean > deleteVentes(
			  @ApiParam(name = "id", value="id of Ventes", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String VentesId) {

		  VentesService.deleteVentes(VentesId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
