package com.housservice.housstock.controller.secteurActivite;

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
import com.housservice.housstock.model.SecteurActivite;
import com.housservice.housstock.repository.SecteurActiviteRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class SecteurActiviteController {

	
	@Autowired
	 private SecteurActiviteRepository secteurActiviteRepository;
	
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	 
	 
	 @GetMapping("/secteurActivite")
	 public List< SecteurActivite > getAllSecteurActivite() {
		 return secteurActiviteRepository.findAll();
		 
	 }
	 
	 @PutMapping("/secteurActivite")
	 public SecteurActivite createSecteurActivite(@Valid @RequestBody SecteurActivite secteurActivite)
	 {
		 secteurActivite.setId("" + sequenceGeneratorService.generateSequence(SecteurActivite.SEQUENCE_NAME));
		 return secteurActiviteRepository.save(secteurActivite);
	 }
	 
	 
	 @PutMapping("/secteurActivite/{id}")
	 public ResponseEntity < SecteurActivite > updateSecteurActivite (@PathVariable(value = "id")String secteurActiviteId,
			 @Valid @RequestBody SecteurActivite secteurActiviteData) throws ResourceNotFoundException {
		 SecteurActivite secteurActivite = secteurActiviteRepository.findById(secteurActiviteId).orElseThrow(()-> new ResourceNotFoundException("SecteurActivite non trouvé pour cet id : " + secteurActiviteId));
		 
		 secteurActivite.setId(secteurActiviteData.getId());
		 secteurActivite.setSecteur(secteurActiviteData.getSecteur());
		 final SecteurActivite updateSecteurActivite = secteurActiviteRepository.save(secteurActivite);
		 return ResponseEntity.ok(updateSecteurActivite);
	 }
	 
	 
	 @DeleteMapping("/secteurActivite/{id}")
	public Map <String , Boolean> deleteSecteurActivite(@PathVariable(value = "id") String secteurActiviteId)
		 throws ResourceNotFoundException{
		 SecteurActivite secteurActivite = secteurActiviteRepository.findById(secteurActiviteId)
					 .orElseThrow(() -> new ResourceNotFoundException("Secteur d'activite non trouvé pour cet id :" + secteurActiviteId));
			 
		 secteurActiviteRepository.delete(secteurActivite);
			 Map < String, Boolean > response = new HashMap < > ();
			 response.put("deleted", Boolean.TRUE);
			 return response;
	 }
	 
	
	
}
