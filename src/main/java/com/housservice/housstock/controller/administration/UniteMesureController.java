package com.housservice.housstock.controller.administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.UniteMesure;
import com.housservice.housstock.model.UniteMesureDetail;
import com.housservice.housstock.repository.UniteMesureRepository;
import com.housservice.housstock.service.SequenceGeneratorService;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UniteMesureController {
	  @Autowired
	  private UniteMesureRepository uniteMesureRepository;

	  @Autowired
	  private SequenceGeneratorService sequenceGeneratorService;
	  
	  @GetMapping("/unites")
	  public List < UniteMesure > getAllUniteMesure() {
	      return uniteMesureRepository.findAll();
	  }

	  @GetMapping("/unites/{id}")
	  public ResponseEntity < UniteMesure > getUniteMesureById(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      UniteMesure uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	    		  .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));
	      return ResponseEntity.ok().body(uniteMesure);
	  }

	  @PutMapping("/unites")
	  public UniteMesure createUniteMesure(@Valid @RequestBody UniteMesure uniteMesure) {
		  uniteMesure.setId("" + sequenceGeneratorService.generateSequence(UniteMesure.SEQUENCE_NAME));
		  if (uniteMesure.getListMultiple() != null) {
			for (UniteMesureDetail uniteMesureDetail : uniteMesure.getListMultiple()) {
				uniteMesureDetail.setId(uniteMesure.getId() + "-" + sequenceGeneratorService.generateSequence(UniteMesure.SEQUENCE_NAME));
			}
		  }
		  if (uniteMesure.getListSousMultiple() != null) {
			  for (UniteMesureDetail uniteMesureDetail : uniteMesure.getListSousMultiple()) {
				  uniteMesureDetail.setId(uniteMesure.getId() + "-" + sequenceGeneratorService.generateSequence(UniteMesure.SEQUENCE_NAME));
			  }
		  }
	      return uniteMesureRepository.save(uniteMesure);
	  }

	  @PutMapping("/unites/{id}")
	  public ResponseEntity < UniteMesure > updateUniteMesure(@PathVariable(value = "id") String uniteMesureId,
	      @Valid @RequestBody UniteMesure uniteMesureData) throws ResourceNotFoundException {
	      UniteMesure uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));

	      uniteMesure.setNom(uniteMesureData.getNom());
	      uniteMesure.setLabel(uniteMesureData.getLabel());
	      for (UniteMesureDetail uniteMesureDetail : uniteMesureData.getListMultiple()) {
			if (StringUtils.isEmpty(uniteMesureDetail.getId())) {
				uniteMesureDetail.setId(uniteMesure.getId() + "-" + sequenceGeneratorService.generateSequence(UniteMesure.SEQUENCE_NAME));
			}
	      }
	      uniteMesure.setListMultiple(uniteMesureData.getListMultiple());
	      for (UniteMesureDetail uniteMesureDetail : uniteMesureData.getListSousMultiple()) {
	    	  if (StringUtils.isEmpty(uniteMesureDetail.getId())) {
	    		  uniteMesureDetail.setId(uniteMesure.getId() + "-" + sequenceGeneratorService.generateSequence(UniteMesure.SEQUENCE_NAME));
	    	  }
	      }
	      uniteMesure.setListSousMultiple(uniteMesureData.getListSousMultiple());
	      final UniteMesure updatedUniteMesure = uniteMesureRepository.save(uniteMesure);
	      return ResponseEntity.ok(updatedUniteMesure);
	  }

	  @DeleteMapping("/unites/{id}")
	  public Map < String, Boolean > deleteUniteMesure(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      UniteMesure uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));

	      uniteMesureRepository.delete(uniteMesure);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
}
