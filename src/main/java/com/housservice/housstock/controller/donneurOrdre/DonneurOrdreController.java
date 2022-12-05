package com.housservice.housstock.controller.donneurOrdre;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.DonneurOrdre;
import com.housservice.housstock.model.dto.DonneurOrdreDto;
import com.housservice.housstock.service.DonneurOrdreService;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/v1/donneurOrdre")
@Api(tags = {"DonneurOrdres Management"})
public class DonneurOrdreController {
	
	private final DonneurOrdreService donneurOrdreService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	  @Autowired
	  public DonneurOrdreController(DonneurOrdreService donneurOrdreService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.donneurOrdreService = donneurOrdreService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
	  
		@GetMapping("/getIdDonneurOrdres/{raisonSociale}")
		@ApiOperation(value = "service to get Id Donneur Ordre by raison sociale.")
		public String getIdDonneurOrdres( 
				@ApiParam(name = "raisonSociale", value="raisonSociale of donneurOrdres", required = true)
				@PathVariable(value = "raisonSociale", required = true) @NotEmpty(message = "{http.error.0001}") String raisonSociale) throws ResourceNotFoundException {
			return donneurOrdreService.getIdDonneurOrdres(raisonSociale);

		}
		
		@GetMapping("/getRaisonSociales")
		@ApiOperation(value = "service to get raison sociale donneur d'ordre")
		public List<String> getRaisonSociales() {
			return donneurOrdreService.getRaisonSociales();

		}
		
		 @GetMapping("/getAllDonneurOrdre")
		 public List< DonneurOrdre > getAllDonneurOrdre() {
			 		
			 return donneurOrdreService.findDonneurOrdreActif();
		 
		 }
		 
		 @GetMapping("/getAllDonneurOrdreNonActive")
		 public List< DonneurOrdre > getAllDonneurOrdreNonActive() {
			 return donneurOrdreService.findDonneurOrdreNonActive();

		 }
		 
		  @GetMapping("/donneurOrdre/{id}")
		  @ApiOperation(value = "service to get one donneur ordre by Id.")
		  public ResponseEntity < DonneurOrdre > getDonneurOrdreById(
				  @ApiParam(name = "id", value="id of donneurOrdre", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String donneurOrdreId)
		  throws ResourceNotFoundException {
			  DonneurOrdre donneurOrdre = donneurOrdreService.getDonneurOrdreById(donneurOrdreId)
		    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), donneurOrdreId)));
		      return ResponseEntity.ok().body(donneurOrdre);
		  }
		  
		  @PutMapping("/addDonneurOrdre")
		  public ResponseEntity<String> createDonneurOrdre(@Valid @RequestBody DonneurOrdreDto donneurOrdreDto) {
			  donneurOrdreService.createNewDonneurOrdre(donneurOrdreDto);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		  }
		  
		  @PutMapping("/updateDonneurOrdre/{idDonneurOrdre}")
		  public ResponseEntity <String> updateDonneurOrdre(
				  @ApiParam(name = "idDonneurOrdre", value="id of donneurOrdre", required = true)
				  @PathVariable(value = "idDonneurOrdre", required = true) @NotEmpty(message = "{http.error.0001}")  String idDonneurOrdre,
		      @Valid @RequestBody(required = true) DonneurOrdreDto donneurOrdreDto) throws ResourceNotFoundException {
			  
			  donneurOrdreService.updateDonneurOrdre(donneurOrdreDto);
		      
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
		  }
		
		  @DeleteMapping("/deleteDonneurOrdre/{id}")
		  @ApiOperation(value = "service to delete one DonneurOrdre by Id.")
		  public Map < String, Boolean > deleteDonneurOrdre(
				  @ApiParam(name = "id", value="id of DonneurOrdre", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String donneurOrdreId)
		  throws ResourceNotFoundException {
		      DonneurOrdre donneurOrdre = donneurOrdreService.getDonneurOrdreById(donneurOrdreId)
		    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), donneurOrdreId)));

		      donneurOrdreService.deleteDonneurOrdre(donneurOrdre);
		      Map < String, Boolean > response = new HashMap < > ();
		      response.put("deleted", Boolean.TRUE);
		      return response;
		  }
		

}
