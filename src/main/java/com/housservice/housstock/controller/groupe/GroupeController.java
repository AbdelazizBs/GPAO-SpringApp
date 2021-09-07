package com.housservice.housstock.controller.groupe;

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
import com.housservice.housstock.model.Groupe;
import com.housservice.housstock.repository.GroupeRepository; 
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class GroupeController {
	
	@Autowired
	 private GroupeRepository groupeRepository;
	
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	 
	 
	 @GetMapping("/groupe")
	 public List< Groupe > getAllGroupe() {
		 return groupeRepository.findAll();
		 
	 }
	 	 
	 @PutMapping("/groupe")
	 public Groupe createGroupe(@Valid @RequestBody Groupe groupe)
	 {
		 groupe.setId("" + sequenceGeneratorService.generateSequence(Groupe.SEQUENCE_NAME));
		 return groupeRepository.save(groupe);
	 }
	 

	 @PutMapping("/groupe/{id}")
	 public ResponseEntity < Groupe > updateGroupe (@PathVariable(value = "id")String groupeId,
			 @Valid @RequestBody Groupe groupeData) throws ResourceNotFoundException {
		 Groupe groupe = groupeRepository.findById(groupeId).orElseThrow(()-> new ResourceNotFoundException("Groupe non trouvé pour cet id : " + groupeId));
		 
		 groupe.setId(groupeData.getId());
		 groupe.setLibelle(groupeData.getLibelle());
		 final Groupe updateGroupe = groupeRepository.save(groupe);
		 return ResponseEntity.ok(updateGroupe);
	 }
	 
	 @DeleteMapping("/groupe/{id}")
	public Map <String , Boolean> deleteGroupe(@PathVariable(value = "id") String groupeId)
		 throws ResourceNotFoundException{
		 Groupe groupe = groupeRepository.findById(groupeId)
					 .orElseThrow(() -> new ResourceNotFoundException("Groupe non trouvé pour cet id :" + groupeId));
			 
		 groupeRepository.delete(groupe);
			 Map < String, Boolean > response = new HashMap < > ();
			 response.put("deleted", Boolean.TRUE);
			 return response;
	 }

}
