package com.housservice.housstock.controller.nomenclatures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.repository.NomenclatureRepository;
import com.housservice.housstock.service.SequenceGeneratorService;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class NomenclatureController {
	  @Autowired
	  private NomenclatureRepository uniteMesureRepository;

	  @Autowired
	  private SequenceGeneratorService sequenceGeneratorService;
	  
	  @GetMapping("/nomenclature")
	  public List < Nomenclature > getAllNomenclature() {
	      return uniteMesureRepository.findAll();
	  }

	  @GetMapping("/nomenclature/{id}")
	  public ResponseEntity < Nomenclature > getNomenclatureById(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      Nomenclature uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	    		  .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));
	      return ResponseEntity.ok().body(uniteMesure);
	  }

	  @PutMapping("/nomenclature")
	  public Nomenclature createNomenclature(@Valid @RequestBody Nomenclature uniteMesure) {
		  uniteMesure.setId("" + sequenceGeneratorService.generateSequence(Nomenclature.SEQUENCE_NAME));
	      return uniteMesureRepository.save(uniteMesure);
	  }

	  @PutMapping("/nomenclature/{id}")
	  public ResponseEntity < Nomenclature > updateNomenclature(@PathVariable(value = "id") String uniteMesureId,
	      @Valid @RequestBody Nomenclature uniteMesureData) throws ResourceNotFoundException {
	      Nomenclature uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));

	      uniteMesure.setNom(uniteMesureData.getNom());
	      uniteMesure.setLabel(uniteMesureData.getLabel());
	      final Nomenclature updatedNomenclature = uniteMesureRepository.save(uniteMesure);
	      return ResponseEntity.ok(updatedNomenclature);
	  }

	  @DeleteMapping("/nomenclature/{id}")
	  public Map < String, Boolean > deleteNomenclature(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      Nomenclature uniteMesure = uniteMesureRepository.findById(uniteMesureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));

	      uniteMesureRepository.delete(uniteMesure);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
}
