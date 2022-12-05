package com.housservice.housstock.controller.etapeProduction;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EtapeProductionDto;
import com.housservice.housstock.service.EtapeProductionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/etapeProduction")
public class EtapeProductionController {
	
	@Autowired 
	private EtapeProductionService etapeProductionService;
	
	  private final MessageHttpErrorProperties messageHttpErrorProperties;
		
	
	  @Autowired
	  public EtapeProductionController(EtapeProductionService etapeProductionService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.etapeProductionService = etapeProductionService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

	 
	 @GetMapping("/getAllEtapes")
	 public List< EtapeProductionDto > getAllEtapeProduction() {
		 return etapeProductionService.getAllEtapeProduction();
	 
	 }

	 @GetMapping("/getNomEtapes")
	 public List< String > getNomEtapes() {
		 return etapeProductionService.getNomEtapes();

	 }
	 

	  @GetMapping("/etapeProduction/{id}")
	  @ApiOperation(value = "service to get one EtapeProduction by Id.")
	  public ResponseEntity < EtapeProduction > getEtapeProductionById(
			  @ApiParam(name = "id", value="id of etapeProduction", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String etapeProductionId)
	  throws ResourceNotFoundException {
		  EtapeProduction etapeProduction = etapeProductionService.getEtapeProductionById(etapeProductionId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), etapeProductionId)));
	      return ResponseEntity.ok().body(etapeProduction);
	  }


	  
	  @PutMapping("/etapeProduction")
	  public ResponseEntity<String> createEtapeProduction(@Valid @RequestBody EtapeProductionDto etapeProductionDto) {
		  
		  etapeProductionService.createNewEtapeProduction(etapeProductionDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
	  
	  
	 
	  @PutMapping("/etapeProduction/{id}")
	  public ResponseEntity <String> updateEtapeProduction(
			  @ApiParam(name = "id", value="id of etapeProduction", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String etapeProductionId,
	      @Valid @RequestBody(required = true) EtapeProductionDto etapeProductionDto) throws ResourceNotFoundException {
		  
		  etapeProductionService.updateEtapeProduction(etapeProductionDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
	 
	  @DeleteMapping("/etapeProduction/{id}")
	  @ApiOperation(value = "service to delete one EtapeProduction by Id.")
	  public Map < String, Boolean > deleteetapeProduction(
			  @ApiParam(name = "id", value="id of etapeProduction", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String etapeProductionId)
	  throws ResourceNotFoundException {
	      EtapeProduction etapeProduction = etapeProductionService.getEtapeProductionById(etapeProductionId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), etapeProductionId)));

	      etapeProductionService.deleteEtapeProduction(etapeProduction);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	 

}
