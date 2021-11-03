package com.housservice.housstock.controller.nomenclatures;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.service.NomenclatureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Nomenclatures Management"})
public class NomenclatureController {
	  
	  private NomenclatureService nomenclatureService;

	  
	  private final MessageHttpErrorProperties messageHttpErrorProperties;
	  
	  @Autowired
	  public NomenclatureController(NomenclatureService nomenclatureService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.nomenclatureService = nomenclatureService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
	  
	  @GetMapping("/nomenclature/{idCompte}/{idParent}")
	  @ResponseBody
	  @ApiOperation(value = "service to get All Nomenclatures for an account with Family type.")
	  public ResponseEntity<List < NomenclatureDto >> getNomenclatureyIdCompteIdParent(
			  @ApiParam(name = "idCompte", value="id of account", required = true)
			  @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte,
			  @ApiParam(name = "idParent", value="id of parent", required = true)
			  @PathVariable(value = "idParent", required = true) @NotEmpty(message = "{http.error.0001}") String idParent) {
		  List<NomenclatureDto> listNomenclatureDto = nomenclatureService.getNomenclatureyIdCompteIdParent(idCompte, idParent);
	      return ResponseEntity.ok().body(listNomenclatureDto);
	  }
	  
	  @GetMapping("/nomenclature/{idCompte}/{idParent}/{sortId}/{sortWay}")
	  @ResponseBody
	  @ApiOperation(value = "service to get All Nomenclatures for an account with Family type.")
	  public ResponseEntity<List < NomenclatureDto >> getAllSortedNomenclature(
			  @ApiParam(name = "idCompte", value="id of account", required = true)
			  @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte,
			  @ApiParam(name = "idParent", value="id of parent", required = true)
			  @PathVariable(value = "idParent", required = true) @NotEmpty(message = "{http.error.0001}") String idParent,
			  @ApiParam(name = "sortId", value="id of colonne will sorted by", required = false)
			  @PathVariable(value = "sortId", required = false) String sortId,
			  @ApiParam(name = "sortWay", value="the sens of sort way (ASC, DESC)", required = false)
			  @PathVariable(value = "sortWay", required = false) String sortWay) {
		  List<NomenclatureDto> listNomenclatureDto = nomenclatureService.getNomenclatureyByIdCompteIdParentSorted(idCompte, idParent, sortId, sortWay);
	      return ResponseEntity.ok().body(listNomenclatureDto);
	  }
	  
	  @GetMapping("/famille-search/{idCompte}/{recherche}")
	  @ApiOperation(value = "service to search Nomenclatures by name or description .")
	  public ResponseEntity<List <NomenclatureDto>> getFamilleSearch(
			  @ApiParam(name = "idCompte", value="id of account", required = true)
			  @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte,
			  @ApiParam(name = "recherche", value="word used to search a Nomenclature", required = true)
			  @PathVariable(value = "recherche") @Size(min = 3, message = "{http.error.0006}") String recherche) {
	      return ResponseEntity.ok().body(nomenclatureService.findFamilyNomenclatureByIdCompte(idCompte, recherche));
	  }	  
	  
	  @PostMapping("/nomenclature/search/{idCompte}")
	  @ApiOperation(value = "service to search Nomenclatures.")
	  public ResponseEntity<List <NomenclatureDto>> getNomenclaturesSearch(
			  @ApiParam(name = "idCompte", value="id of account", required = true)
			  @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte,
			  @RequestBody NomenclatureDto nomenclatureDto) {
	      return ResponseEntity.ok().body(nomenclatureService.findNomenclatures(idCompte, nomenclatureDto));
	  }	

	  @GetMapping("/nomenclature/{id}")
	  @ApiOperation(value = "service to get one Nomenclature by Id.")
	  public ResponseEntity < Nomenclature > getNomenclatureById(
			  @ApiParam(name = "id", value="id of nomenclature", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
	  throws ResourceNotFoundException {
	      Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(), nomenclatureId)));
	      return ResponseEntity.ok().body(nomenclature);
	  }

	  @PutMapping("/nomenclature")
	  public ResponseEntity<String> createNomenclature(@Valid @RequestBody NomenclatureDto nomenclatureDto) {
		  
		  nomenclatureService.createNewNomenclature(nomenclatureDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getErro0003());
	  }

	  @PutMapping("/nomenclature/{id}")
	  public ResponseEntity <String> updateNomenclature(
			  @ApiParam(name = "id", value="id of nomenclature", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String nomenclatureId,
	      @Valid @RequestBody(required = true) NomenclatureDto nomenclatureDto) throws ResourceNotFoundException {
		  
		  nomenclatureService.updateNomenclature(nomenclatureDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getErro0004());
	  }

	  @DeleteMapping("/nomenclature/{id}")
	  @ApiOperation(value = "service to delete one Nomenclature by Id.")
	  public Map < String, Boolean > deleteNomenclature(
			  @ApiParam(name = "id", value="id of nomenclature", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String nomenclatureId)
	  throws ResourceNotFoundException {
	      Nomenclature nomenclature = nomenclatureService.getNomenclatureById(nomenclatureId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(), nomenclatureId)));

	      nomenclatureService.deleteNomenclature(nomenclature);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
}
