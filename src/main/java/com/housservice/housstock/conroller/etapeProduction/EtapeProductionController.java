package com.housservice.housstock.conroller.etapeProduction;

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
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.repository.EtapeProductionRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class EtapeProductionController {
	
	@Autowired
	 private EtapeProductionRepository etapeProductionRepository;
	
	@Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	

	@GetMapping("/etapeProduction")
	 public List<EtapeProduction> getAllEtapeProduction() {
		 return etapeProductionRepository.findAll();
		 
	 }
	
	
	 @PutMapping("/etapeProduction")
	 public EtapeProduction createEtapeProduction(@Valid @RequestBody EtapeProduction etapeProduction)
	 {
		 etapeProduction.setId("" + sequenceGeneratorService.generateSequence(EtapeProduction.SEQUENCE_NAME));
		 return etapeProductionRepository.save(etapeProduction);
	 }
	 
	 
	 @PutMapping("/etapeProduction/{id}")
	 public ResponseEntity < EtapeProduction > updateEtapeProduction (@PathVariable(value = "id")String etapeProductionId,
			 @Valid @RequestBody EtapeProduction etapeProductionData) throws ResourceNotFoundException {
		 EtapeProduction etapeProduction = etapeProductionRepository.findById(etapeProductionId).orElseThrow(()-> new ResourceNotFoundException("Etape production non trouvé pour cet id : " + etapeProductionId));
		 
		 etapeProduction.setId(etapeProductionData.getId());
		 etapeProduction.setNom_etape(etapeProductionData.getNom_etape());
		 etapeProduction.setType_etape(etapeProductionData.getType_etape());
		 final EtapeProduction updateEtapeProduction = etapeProductionRepository.save(etapeProduction);
		 return ResponseEntity.ok(updateEtapeProduction);
	 }
	 
	 
	 
	 @DeleteMapping("/etapeProduction/{id}")
		public Map <String , Boolean> deleteEtapeProduction(@PathVariable(value = "id") String etapeProductionId)
			 throws ResourceNotFoundException{
		 EtapeProduction etapeProduction = etapeProductionRepository.findById(etapeProductionId)
						 .orElseThrow(() -> new ResourceNotFoundException("Etape production non trouvé pour cet id :" + etapeProductionId));
				 
		 etapeProductionRepository.delete(etapeProduction);
				 Map < String, Boolean > response = new HashMap < > ();
				 response.put("deleted", Boolean.TRUE);
				 return response;
		 }
	
	

}
