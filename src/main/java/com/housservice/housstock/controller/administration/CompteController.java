package com.housservice.housstock.controller.administration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.service.SequenceGeneratorService;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CompteController {
	  @Autowired
	  private ComptesRepository comptesRepository;

	  @Autowired
	  private SequenceGeneratorService sequenceGeneratorService;
	  
	  @GetMapping("/comptes/{id}")
	  public ResponseEntity < Comptes > getUniteMesureById(@PathVariable(value = "id") String compteId)
	  throws ResourceNotFoundException {
	      Comptes compte = comptesRepository.findById(compteId)
	    		  .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé pour cet id :: " + compteId));
	      return ResponseEntity.ok().body(compte);
	  }

	  @PutMapping("/comptes")
	  public Comptes createUniteMesure(@Valid @RequestBody Comptes compte) {
		  compte.setId("" + sequenceGeneratorService.generateSequence(Comptes.SEQUENCE_NAME));
	      return comptesRepository.save(compte);
	  }

	  @PutMapping("/comptes/{id}")
	  public ResponseEntity < Comptes > updateUniteMesure(@PathVariable(value = "id") String compteId,
	      @Valid @RequestBody Comptes compteData) throws ResourceNotFoundException {
		  Comptes compte = comptesRepository.findById(compteId)
	          .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé pour cet id :: " + compteId));

	      compte.setEmail(compteData.getEmail());
	      compte.setPassword(compteData.getPassword());
	      final Comptes updatedCompte = comptesRepository.save(compte);
	      return ResponseEntity.ok(updatedCompte);
	  }

//	  @DeleteMapping("/comptes/{id}")
//	  public Map < String, Boolean > deleteUniteMesure(@PathVariable(value = "id") Long uniteMesureId)
//	  throws ResourceNotFoundException {
//		  Comptes compte = comptesRepository.findById(uniteMesureId)
//	          .orElseThrow(() -> new ResourceNotFoundException("Unite de Mesure non trouvé pour cet id :: " + uniteMesureId));
//
//		  comptesRepository.delete(compte);
//	      Map < String, Boolean > response = new HashMap < > ();
//	      response.put("deleted", Boolean.TRUE);
//	      return response;
//	  }
}
