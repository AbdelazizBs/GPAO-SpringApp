package com.housservice.housstock.controller.brancheActivite;

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
import com.housservice.housstock.model.BrancheActivite;
import com.housservice.housstock.repository.BrancheActiviteRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class BrancheActiviteController {

	@Autowired
	 private BrancheActiviteRepository brancheActiviteRepository;
	
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	 
	 
	 @GetMapping("/brancheActivite")
	 public List< BrancheActivite > getAllBrancheActivite() {
		 return brancheActiviteRepository.findAll();
		 
	 }
	 
	 @PutMapping("/brancheActivite")
	 public BrancheActivite createBrancheActivite(@Valid @RequestBody BrancheActivite brancheActivite)
	 {
		 brancheActivite.setId("" + sequenceGeneratorService.generateSequence(BrancheActivite.SEQUENCE_NAME));
		 return brancheActiviteRepository.save(brancheActivite);
	 }
	 
	 
	 @PutMapping("/brancheActivite/{id}")
	 public ResponseEntity < BrancheActivite > updateBrancheActivite (@PathVariable(value = "id")String brancheActiviteId,
			 @Valid @RequestBody BrancheActivite brancheActiviteData) throws ResourceNotFoundException {
		 BrancheActivite brancheActivite = brancheActiviteRepository.findById(brancheActiviteId).orElseThrow(()-> new ResourceNotFoundException("BrancheActivite non trouvé pour cet id : " + brancheActiviteId));
		 
		 brancheActivite.setId(brancheActiviteData.getId());
		 brancheActivite.setBranche(brancheActiviteData.getBranche());
		 final BrancheActivite updateBrancheActivite = brancheActiviteRepository.save(brancheActivite);
		 return ResponseEntity.ok(updateBrancheActivite);
	 }
	 
	 
	 @DeleteMapping("/brancheActivite/{id}")
	public Map <String , Boolean> deleteBrancheActivite(@PathVariable(value = "id") String brancheActiviteId)
		 throws ResourceNotFoundException{
		 BrancheActivite brancheActivite = brancheActiviteRepository.findById(brancheActiviteId)
					 .orElseThrow(() -> new ResourceNotFoundException("Branche d'activite non trouvé pour cet id :" + brancheActiviteId));
			 
		 brancheActiviteRepository.delete(brancheActivite);
			 Map < String, Boolean > response = new HashMap < > ();
			 response.put("deleted", Boolean.TRUE);
			 return response;
	 }
	 
	
}
