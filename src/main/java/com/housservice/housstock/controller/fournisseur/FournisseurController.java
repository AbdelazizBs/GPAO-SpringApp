package com.housservice.housstock.controller.fournisseur;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.service.FournisseurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/fournisseur")
@Api(tags = { "fournisseurs Management" })
public class FournisseurController {
	
	private FournisseurService fournisseurService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	public FournisseurController(FournisseurService fournisseurService,
			MessageHttpErrorProperties messageHttpErrorProperties) {
		this.fournisseurService = fournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}

	@PutMapping("/addFournisseur")
	@ApiOperation(value = "service to add new Fournisseur")
	public ResponseEntity<String> addFournisseur(@Valid  @RequestBody FournisseurDto fournisseurDto)   {
		  fournisseurService.addFournisseur(fournisseurDto);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

	}
	@PutMapping("/updateFournisseur/{idFournisseur}")
	@ApiOperation(value = "service to update  Fournisseur")
	public ResponseEntity<String> updateFournisseur(@Valid  @RequestBody FournisseurDto fournisseurDto,
												  @PathVariable(value = "idFournisseur", required = true) String idFournisseur) throws ResourceNotFoundException {
		  fournisseurService.updateFournisseur(fournisseurDto,idFournisseur);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

	}

	@GetMapping("/getFournisseurById/{id}")
	@ApiOperation(value = "service to get one fournisseur by Id.")
	public ResponseEntity <FournisseurDto> getFournisseurById(
			@ApiParam(name = "id", value="id of fournisseur", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
			throws ResourceNotFoundException {
		FournisseurDto fournisseur = fournisseurService.getFournisseurById(fournisseurId);
		if (fournisseur == null) {
			ResponseEntity.badRequest();
		}

		return ResponseEntity.ok().body(fournisseur);
	}


	@PutMapping("/mettreEnVeille/{idFournisseur}")
	public ResponseEntity<String> mettreEnVeille(
			@ApiParam(name = "idFournisseur", value = "id of fournisseur", required = true) @PathVariable(value = "idfournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur)
			throws ResourceNotFoundException {
		fournisseurService.mettreEnVeille(idFournisseur);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}



	@GetMapping("/getAllFournisseur")
	@ApiOperation(value = "service to get get All Fournisseur")
	public ResponseEntity<Map<String, Object>> getAllFournisseur(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size){
		return fournisseurService.getAllFournisseur(page,size);

	}
	@GetMapping("/getAllFournisseurEnVeille")
	@ApiOperation(value = "service to get get All Fournisseur En Veille ")
	public ResponseEntity<Map<String, Object>> getAllFournisseurEnVeille(@RequestParam(defaultValue = "0") int page,
																	   @RequestParam(defaultValue = "3") int size) {
		return fournisseurService.getAllFournisseurEnVeille(page, size);

	}
	@GetMapping("/search")
	@ApiOperation(value = "service to filter fournisseur ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam boolean enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return fournisseurService.find(textToFind, page, size,enVeille);

	}

	@DeleteMapping("/deleteFournisseur/{id}")
	@ApiOperation(value = "service to delete one Fournisseur by Id.")
	public Map<String, Boolean> deleteFournisseur(
			@ApiParam(name = "id", value = "id of fournisseur", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId) {

		fournisseurService.deleteFournisseur(fournisseurId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/deleteSelectedFournisseur/{idFournisseursSelected}")
	@ApiOperation(value = "service to delete many Fournisseur by Id.")
	public Map<String, Boolean> deleteFournisseurSelected(
			@ApiParam(name = "idFournisseursSelected", value = "ids of fournisseur Selected", required = true) @PathVariable(value = "idFournisseursSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idFournisseursSelected) {
		fournisseurService.deleteFournisseurSelected(idFournisseursSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
