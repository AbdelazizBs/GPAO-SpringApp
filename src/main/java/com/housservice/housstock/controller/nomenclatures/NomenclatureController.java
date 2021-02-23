package com.housservice.housstock.controller.nomenclatures;

import java.util.ArrayList;
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
	  private NomenclatureRepository nomenclatureRepository;

	  @Autowired
	  private SequenceGeneratorService sequenceGeneratorService;
	  
	  @GetMapping("/nomenclature")
	  public List < Nomenclature > getAllNomenclature() {
	      return nomenclatureRepository.findAll();
	  }
	  
	  @GetMapping("/nomenclature/Familles")
	  public List < Nomenclature > getAllNomenclatureFamillesRacine() {
	      return nomenclatureRepository.findByTypeAndIdParent(Nomenclature.TYPE_FAMILLE, null);
	  }
	  
	  @GetMapping("/nomenclature-search/{recherche}")
	  public List < Nomenclature > getSearchNomenclature(@PathVariable(value = "recherche") String recherche) {
	      if (StringUtils.isEmpty(recherche)) {
	    	 return new ArrayList<>();
	      }
		  return nomenclatureRepository.findByNomLikeOrLabelLikeOrDescriptionLikeAndTypeAllIgnoreCase(recherche, recherche, recherche, Nomenclature.TYPE_FAMILLE);
	  }	  

	  @GetMapping("/nomenclature/{id}")
	  public ResponseEntity < Nomenclature > getNomenclatureById(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      Nomenclature uniteMesure = nomenclatureRepository.findById(uniteMesureId)
	    		  .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));
	      return ResponseEntity.ok().body(uniteMesure);
	  }

	  @PutMapping("/nomenclature")
	  public Nomenclature createNomenclature(@Valid @RequestBody Nomenclature nomenclature) {
		  nomenclature.setId("" + sequenceGeneratorService.generateSequence(Nomenclature.SEQUENCE_NAME));
	      return nomenclatureRepository.save(nomenclature);
	  }

	  @PutMapping("/nomenclature/{id}")
	  public ResponseEntity < Nomenclature > updateNomenclature(@PathVariable(value = "id") String nomenclatureId,
	      @Valid @RequestBody Nomenclature nomenclatureData) throws ResourceNotFoundException {
	      Nomenclature nomenclature = nomenclatureRepository.findById(nomenclatureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Nomenclature non trouvé pour cet id :: " + nomenclatureId));
	      
	      nomenclature.setIdCompte(nomenclatureData.getIdCompte());
	      nomenclature.setNom(nomenclatureData.getNom());
	      nomenclature.setDescription(nomenclatureData.getDescription());
	      nomenclature.setLabel(nomenclatureData.getLabel());
	      nomenclature.setType(nomenclatureData.getType());
	      final Nomenclature updatedNomenclature = nomenclatureRepository.save(nomenclature);
	      return ResponseEntity.ok(updatedNomenclature);
	  }

	  @DeleteMapping("/nomenclature/{id}")
	  public Map < String, Boolean > deleteNomenclature(@PathVariable(value = "id") String uniteMesureId)
	  throws ResourceNotFoundException {
	      Nomenclature uniteMesure = nomenclatureRepository.findById(uniteMesureId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));

	      nomenclatureRepository.delete(uniteMesure);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
}
