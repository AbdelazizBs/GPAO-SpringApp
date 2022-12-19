package com.housservice.housstock.controller.matiere;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.MatiereDto;
import com.housservice.housstock.service.MatiereService;
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
@Api(tags = {"Matieres Management"})
public class MatiereController {
	
	private MatiereService matiereService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public MatiereController(MatiereService matiereService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.matiereService = matiereService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/matiere")
	 public List< MatiereDto > getAllMatiere() {
		 		
		 return matiereService.getAllMatiere();
		 	 
	 }

    
    @GetMapping("/matiere/{id}")
	@ApiOperation(value = "service to get one Matiere by Id.")
	  public ResponseEntity < MatiereDto > getMatiereById(
			  @ApiParam(name = "id", value="id of matiere", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String matiereId)
	  throws ResourceNotFoundException {
    	MatiereDto matiere = matiereService.getMatiereById(matiereId);
		  if (matiere == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(matiere);
	  }
    
    @PutMapping("/matiere")
	  public ResponseEntity<String> createMatiere(@Valid @RequestBody MatiereDto matiereDto) {
		  
    	  matiereService.createNewMatiere(matiereDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/matiere/{id}")
	  public ResponseEntity <String> updateMatiere(
			  @ApiParam(name = "id", value="id of matiere", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String matiereId,
	          @Valid @RequestBody(required = true) MatiereDto matiereDto) throws ResourceNotFoundException {
		  
    	  matiereService.updateMatiere(matiereDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/matiere/{id}")
	  @ApiOperation(value = "service to delete one Matiere by Id.")
	  public Map < String, Boolean > deleteMatiere(
			  @ApiParam(name = "id", value="id of matiere", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String matiereId) {

		  matiereService.deleteMatiere(matiereId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}
