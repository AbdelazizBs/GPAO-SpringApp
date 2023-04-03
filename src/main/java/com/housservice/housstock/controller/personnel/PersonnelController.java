package com.housservice.housstock.controller.personnel;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.service.PersonnelService;
import com.housservice.housstock.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/personnel")
@Api(tags = { "personnels Management" })
public class PersonnelController {

	private final PersonnelService personnelService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final
	PictureService pictureService;

	public PersonnelController(PersonnelService personnelService,
							   MessageHttpErrorProperties messageHttpErrorProperties, PictureService pictureService) {
		this.personnelService = personnelService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.pictureService = pictureService;
	}

	@PutMapping("/addPersonnel")
	@ApiOperation(value = "service to add new Personnel")
	public ResponseEntity<String> addPersonnel(@Valid  @RequestBody PersonnelDto personnelDto)   {
			personnelService.addPersonnel(personnelDto);
			return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	}
	@PutMapping("/updatePersonnel/{idPersonnel}")
	@ApiOperation(value = "service to update  Personnel")
	public ResponseEntity<String> updatePersonnel(@Valid  @RequestBody PersonnelDto personnelDto,
												  @PathVariable(value = "idPersonnel", required = true) String idPersonnel) throws ResourceNotFoundException {
		  personnelService.updatePersonnel(personnelDto,idPersonnel);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

	}

	@GetMapping("/getPersonnelById/{id}")
	@ApiOperation(value = "service to get one Utilisateur by Id.")
	public ResponseEntity <PersonnelDto> getPersonnelById(
			@ApiParam(name = "id", value="id of utilisateur", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId)
			throws ResourceNotFoundException {
		PersonnelDto utilisateur = personnelService.getPersonnelById(utilisateurId);
		if (utilisateur == null) {
			ResponseEntity.badRequest();
		}

		return ResponseEntity.ok().body(utilisateur);
	}




	@PutMapping("/mettreEnVeille/{idPersonnel}")
	public ResponseEntity<String> mettreEnVeille(
			@ApiParam(name = "idPersonnel", value = "id of personnel", required = true) @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel)
			throws ResourceNotFoundException {
		personnelService.mettreEnVeille(idPersonnel);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}



	@GetMapping("/onSortActivePersonnel")
	@ApiOperation(value = "service to get get All active Personnel ordered and sorted by params")
	public ResponseEntity<Map<String, Object>> onSortActivePersonnel(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size,
													  	 @RequestParam(defaultValue = "field") String field,
													  @RequestParam(defaultValue = "order") String order){
		return personnelService.onSortActivePersonnel(page,size,field,order);

	}

	@GetMapping("/onSortPersonnelNotActive")
	@ApiOperation(value = "service to get get All  Personnel not active ordered and sorted by params")
	public ResponseEntity<Map<String, Object>> onSortPersonnelNotActive(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size,
													  	 @RequestParam(defaultValue = "field") String field,
													  @RequestParam(defaultValue = "order") String order){
		return personnelService.onSortPersonnelNotActive(page,size,field,order);

	}
	@GetMapping("/getAllPersonnel")
	@ApiOperation(value = "service to get get All Personnel")
	public ResponseEntity<Map<String, Object>> getAllPersonnel(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size){
		return personnelService.getAllPersonnel(page,size);

	}
	@GetMapping("/getAllPersonnelEnVeille")
	@ApiOperation(value = "service to get get All Personnel En Veille ")
	public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(@RequestParam(defaultValue = "0") int page,
																	   @RequestParam(defaultValue = "3") int size) {
		return personnelService.getAllPersonnelEnVeille(page, size);

	}
	@GetMapping("/search")
	@ApiOperation(value = "service to filter personnel ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam boolean enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return personnelService.search(textToFind, page, size,enVeille);

	}

	@DeleteMapping("/deletePersonnel/{id}")
	@ApiOperation(value = "service to delete one Personnel by Id.")
	public Map<String, Boolean> deletePersonnel(
			@ApiParam(name = "id", value = "id of personnel", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

		personnelService.deletePersonnel(utilisateurId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/deleteSelectedPersonnel/{idPersonnelsSelected}")
	@ApiOperation(value = "service to delete many Personnel by Id.")
	public Map<String, Boolean> deletePersonnelSelected(
			@ApiParam(name = "idPersonnelsSelected", value = "ids of personnel Selected", required = true) @PathVariable(value = "idPersonnelsSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idPersonnelsSelected) {
		personnelService.deletePersonnelSelected(idPersonnelsSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	@DeleteMapping("/removePic/{idPicture}")
	@ApiOperation(value = "service to delete one Picture by Id.")
	public Map<String, Boolean> deletePicture(
			@ApiParam(name = "id", value = "id of picture", required = true) @PathVariable(value = "idPicture", required = true) @NotEmpty(message = "{http.error.0001}") String idPicture) {
		pictureService.deleteImg(idPicture);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@GetMapping("/getPersonnalByMonth")
	public int getPersonnalByMonth(){
		return personnelService.getPersonnalByMonth();
	}

	@GetMapping("/getallPersonnal")
	public int getallPersonnal(){
		return personnelService.getallPersonnal();
	}

	@GetMapping("/getPersActifListe")
	public List<Integer> getPersActifListe(){
		return personnelService.getPersListe(false);
	}
	@GetMapping("/getPersNoActifListe")
	public List<Integer> getPersNoActifListe(){
		return personnelService.getPersListe(true);
	}
	@PutMapping("/restaurer/{id}")
	public ResponseEntity<String> restaurer(
			@ApiParam(name = "idPersonnel", value = "id of personnel", required = true) @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String id)
			throws ResourceNotFoundException {
		personnelService.Restaurer(id);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}


}
