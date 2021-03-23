package com.housservice.housstock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.housservice.housstock.model.MetaData;
import com.housservice.housstock.repository.MetaDataRepository;
import com.housservice.housstock.service.SequenceGeneratorService;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MetaDataController {
	  @Autowired
	  private MetaDataRepository metaDataRepository;

	  @Autowired
	  private SequenceGeneratorService sequenceGeneratorService;
	  
	  @GetMapping("/metadata/list/{property}/{value}")
	  public List < MetaData > getAllMetaData(@PathVariable String property, @PathVariable String value) {
		  
		  List<MetaData> resultat = new ArrayList<>();
		  
		  if (StringUtils.isNotEmpty(property)) {
			  switch (property) {
			  case "catalogue":
				  resultat = metaDataRepository.findByCatalogue(value);
				  
				  break;
				  
			  default:
				  break;
			  }
			
		}
	      return resultat;
	  }

	  @GetMapping("/metadata/{id}")
	  public ResponseEntity < MetaData > getMetaDataById(@PathVariable(value = "id") String metaDataId)
	  throws ResourceNotFoundException {
	      MetaData metaData = metaDataRepository.findById(metaDataId)
	    		  .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + metaDataId));
	      return ResponseEntity.ok().body(metaData);
	  }

	  @PutMapping("/metadata")
	  public MetaData createMetaData(@Valid @RequestBody MetaData metaData) {
		  metaData.setId("" + sequenceGeneratorService.generateSequence(MetaData.SEQUENCE_NAME));
	      return metaDataRepository.save(metaData);
	  }

	  @PutMapping("/metadata/{id}")
	  public ResponseEntity < MetaData > updateMetaData(@PathVariable(value = "id") String metaDataId,
	      @Valid @RequestBody MetaData metaDataData) throws ResourceNotFoundException {
	      MetaData metaData = metaDataRepository.findById(metaDataId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + metaDataId));

	      metaData.setCatalogue(metaDataData.getCatalogue());
	      metaData.setDescription(metaDataData.getDescription());
	      metaData.setImage(metaDataData.getImage());
	      metaData.setTitre(metaDataData.getTitre());
	      metaData.setUrl(metaDataData.getUrl());
	      metaData.setData(metaDataData.getData());
	      
	      final MetaData updatedMetaData = metaDataRepository.save(metaData);
	      return ResponseEntity.ok(updatedMetaData);
	  }

	  @DeleteMapping("/metadata/{id}")
	  public Map < String, Boolean > deleteMetaData(@PathVariable(value = "id") String metaDataId)
	  throws ResourceNotFoundException {
	      MetaData metaData = metaDataRepository.findById(metaDataId)
	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + metaDataId));

	      metaDataRepository.delete(metaData);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
}
