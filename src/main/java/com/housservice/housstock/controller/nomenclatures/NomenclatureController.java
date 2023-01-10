package com.housservice.housstock.controller.nomenclatures;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.service.NomenclatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@CrossOrigin
@RestController
@RequestMapping("/api/v1/nomenclature")
@Api(tags = {"Nomenclatures Management"})
@Validated
public class NomenclatureController {
	
	private final NomenclatureService nomenclatureService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	@Autowired
	public NomenclatureController(NomenclatureService nomenclatureService, MessageHttpErrorProperties messageHttpErrorProperties)
	{

		this.nomenclatureService = nomenclatureService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		
	}

	@GetMapping("/getAllNomenclature")
	@ApiOperation(value = "service to get tout les nomenclatures ")
	public ResponseEntity<Map<String, Object>> getAllNomenclature(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return nomenclatureService.findNomenclatureActif(page,size);

	}


	@GetMapping("/nomenclature/{id}")
	@ApiOperation(value = "service to get one Nomenclature by Id.")
	public ResponseEntity < Nomenclature > getNomenclatureById(
			@ApiParam(name = "id", value="id of nomenclature", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
					throws ResourceNotFoundException {

		Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));
		return ResponseEntity.ok().body(nomenclature);
	}


	@GetMapping("/getIdNomenclatures/{NomNomenclature}")
	@ApiOperation(value = "service to get Id Nomenclature by nomNomenclature.")
	public ResponseEntity<Map<String, Object>>  getIdNomenclatures(  @ApiParam(name = "nomNomenclature", value="nomNomenclature of Nomenclatures", required = true)
	@PathVariable(value = "nomNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String nomNomenclature) throws ResourceNotFoundException {
		return nomenclatureService.getIdNomenclatures(nomNomenclature);
	}
	

	@GetMapping("/getNomNomenclatures")
	@ApiOperation(value = "service to get one NomNomenclature")
	public List<String> getNomNomenclatures() {
		return nomenclatureService.getNomNomenclatures();
	}


	@GetMapping("/getAllNomenclatureNonActive")
	public ResponseEntity<Map<String, Object>> getAllNomenclatureNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return nomenclatureService.findNomenclatureNonActive(page,size);

	}


	@GetMapping("/search")
	@ApiOperation(value = "service to filter nomenclatures ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
			@RequestParam boolean enVeille,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		return nomenclatureService.search(textToFind, page, size,enVeille);

	}


	@DeleteMapping("/deleteSelectedNomenclature/{idNomenclaturesSelected}")
	@ApiOperation(value = "service to delete many Nomenclature by Id.")
	public Map<String, Boolean> deleteNomenclatureSelected(
			@ApiParam(name = "idNomenclaturesSelected", value = "ids of nomenclature Selected", required = true) @PathVariable(value = "idNomenclaturesSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idNomenclaturesSelected) {
		nomenclatureService.deleteNomenclatureSelected(idNomenclaturesSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping(value = "/addNomenclature")
	public ResponseEntity<String> createNewNomenclature(
					
			@RequestParam("nomNomenclature")
			@NotEmpty
			String nomNomenclature,
			
			@RequestParam("description")
			@NotEmpty
			String description,
			
			@RequestParam("type")
			@NotEmpty
			String type,
			
			@RequestParam("nature")
			@NotEmpty
			String nature,
			
			@RequestParam("categorie")
			@NotEmpty
			String categorie,
			
			
			@RequestParam("images") MultipartFile[] images

			) throws ResourceNotFoundException {

		nomenclatureService.createNewNomenclature(nomNomenclature, description, type, nature, categorie, images);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	}


	@PutMapping("/updateNomenclature/{idNomenclature}")
	public ResponseEntity <String> updateNomenclature(
			@ApiParam(name = "idNomenclature", value="id of Nomenclature", required = true)
			@PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}")  String idNomenclature,
			@RequestParam("nomNomenclature") String nomNomenclature,
			@RequestParam("description") String description,
			@RequestParam("type") String type,
			@RequestParam("nature") String nature,	
			@RequestParam("categorie") String categorie,
	

			@RequestParam("images") MultipartFile[] images) throws ResourceNotFoundException {

		nomenclatureService.updateNomenclature(idNomenclature, nomNomenclature, description, type, nature, categorie, images);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}
	
	@PutMapping("/miseEnVeille/{idNomenclature}")
	public ResponseEntity <String> miseEnVeille(
			@ApiParam(name = "idNomenclature", value="id of nomenclature", required = true)
			@PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}")  String idNomenclature,
			@RequestBody(required = true) NomenclatureDto nomenclatureDto) throws ResourceNotFoundException {

		    nomenclatureService.miseEnVeille(idNomenclature);

		    return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}


	@DeleteMapping("/deleteNomenclature/{id}")
	@ApiOperation(value = "service to delete one Nomenclature by Id.")
	public Map < String, Boolean > deleteclient(
			@ApiParam(name = "id", value="id of nomenclature", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
					throws ResourceNotFoundException {
		Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));

		nomenclatureService.deleteNomenclature(nomenclature);
		Map < String, Boolean > response = new HashMap < > ();
		response.put("deleted", Boolean.TRUE);
		return response;
	}



	@DeleteMapping("/removePictures/{idNomenclature}")
	@ApiOperation(value = "service to delete all  Picture by idNomenclature and idPicture.")
	public Map< String, Boolean > removePictures(
			@ApiParam(name = "idNomenclature", value="id of nomenclature", required = true)
			@PathVariable(value = "idNomenclature", required = true) @NotEmpty(message = "{http.error.0001}") String idNomenclature)
					throws ResourceNotFoundException {
		nomenclatureService.removePictures(idNomenclature);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/removePicture/{idPicture}")
	@ApiOperation(value = "service to delete one Picture by Id.")
	public Map< String, Boolean > removePicture(
			@ApiParam(name = "idPicture", value="id of picture", required = true)
			@PathVariable(value = "idPicture", required = true) @NotEmpty(message = "{http.error.0001}") String idPicture)
					throws ResourceNotFoundException {
		nomenclatureService.removePicture(idPicture);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


}
