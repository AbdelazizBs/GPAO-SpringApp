package com.housservice.housstock.controller.matiere;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.dto.MatiereDto;
import com.housservice.housstock.service.MatiereService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/v1/matiere")
@Api(tags = {"Matieres Management"})
public class MatiereController {
	
	private MatiereService matiereService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
      
    @Autowired
	  public MatiereController(MatiereService matiereService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.matiereService = matiereService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @PutMapping("/addMatiere")
	@ApiOperation(value = "service to add new Matiere")
	public ResponseEntity<String> addMatiere(@Valid  @RequestBody MatiereDto matiereDto)   {
		  matiereService.addMatiere(matiereDto);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

	}
	@PutMapping("/updateMatiere/{idMatiere}")
	@ApiOperation(value = "service to update  Matiere")
	public ResponseEntity<String> updateMatiere(@Valid  @RequestBody MatiereDto matiereDto,
												  @PathVariable(value = "idMatiere", required = true) String idMatiere) throws ResourceNotFoundException {
		  matiereService.updateMatiere(matiereDto,idMatiere);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

	}

	@GetMapping("/getMatiereById/{id}")
	@ApiOperation(value = "service to get one Utilisateur by Id.")
	public ResponseEntity <MatiereDto> getMatiereById(
			@ApiParam(name = "id", value="id of utilisateur", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId)
			throws ResourceNotFoundException {
		MatiereDto utilisateur = matiereService.getMatiereById(utilisateurId);
		if (utilisateur == null) {
			ResponseEntity.badRequest();
		}

		return ResponseEntity.ok().body(utilisateur);
	}

	@PutMapping("/mettreEnVeille/{idMatiere}")
	public ResponseEntity<String> mettreEnVeille(
			@ApiParam(name = "idMatiere", value = "id of matiere", required = true) @PathVariable(value = "idMatiere", required = true) @NotEmpty(message = "{http.error.0001}") String idMatiere)
			throws ResourceNotFoundException {
		matiereService.mettreEnVeille(idMatiere);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}

	@GetMapping("/getAllMatiere")
	@ApiOperation(value = "service to get get All Matiere")
	public ResponseEntity<Map<String, Object>> getAllMatiere(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size){
		return matiereService.getAllMatiere(page,size);

	}
	
	@GetMapping("/getAllMatiereEnVeille")
	@ApiOperation(value = "service to get get All Matiere En Veille ")
	public ResponseEntity<Map<String, Object>> getAllMatiereEnVeille(@RequestParam(defaultValue = "0") int page,
																	   @RequestParam(defaultValue = "3") int size) {
		return matiereService.getAllMatiereEnVeille(page, size);

	}
		
	
	@GetMapping("/search")
	@ApiOperation(value = "service to filter matiere ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam boolean enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return matiereService.find(textToFind, page, size,enVeille);

	}
		

	@DeleteMapping("/deleteMatiere/{id}")
	@ApiOperation(value = "service to delete one Matiere by Id.")
	public Map<String, Boolean> deleteMatiere(
			@ApiParam(name = "id", value = "id of matiere", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

		matiereService.deleteMatiere(utilisateurId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/deleteSelectedMatiere/{idMatieresSelected}")
	@ApiOperation(value = "service to delete many Matiere by Id.")
	public Map<String, Boolean> deleteMatiereSelected(
			@ApiParam(name = "idMatieresSelected", value = "ids of matiere Selected", required = true) @PathVariable(value = "idMatieresSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idMatieresSelected) {
		matiereService.deleteMatiereSelected(idMatieresSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}

