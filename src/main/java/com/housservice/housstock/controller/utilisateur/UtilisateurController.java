package com.housservice.housstock.controller.utilisateur;

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
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.repository.UtilisateurRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UtilisateurController {
	
	@Autowired
	 private UtilisateurRepository UtilisateurRepository;
	
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	 
	 
	 @GetMapping("/utilisateur")
	 public List< Utilisateur > getAllUtilisateur() {
		 return UtilisateurRepository.findAll();
		 
	 }
	 	 
	 @PutMapping("/utilisateur")
	 public Utilisateur createUtilisateur(@Valid @RequestBody Utilisateur utilisateur)
	 {
		 utilisateur.setId("" + sequenceGeneratorService.generateSequence(Utilisateur.SEQUENCE_NAME));
		 return UtilisateurRepository.save(utilisateur);
	 }
	 
	 @PutMapping("/utilisateur/{id}")
	 public ResponseEntity < Utilisateur > updateUtilisateur (@PathVariable(value = "id")String utilisateurId,
			 @Valid @RequestBody Utilisateur utilisateurData) throws ResourceNotFoundException {
		 Utilisateur utilisateur = UtilisateurRepository.findById(utilisateurId).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé pour cet id :: " + utilisateurId));
		 
		 utilisateur.setId(utilisateurData.getId());
		 utilisateur.setNom(utilisateurData.getNom());
		 utilisateur.setPrenom(utilisateurData.getPrenom());
		 final Utilisateur updateUtilisateur = UtilisateurRepository.save(utilisateur);
		 return ResponseEntity.ok(updateUtilisateur);
	 }
	 
	 @DeleteMapping("/utilisateur/{id}")
	public Map <String , Boolean> deleteUtilisateur(@PathVariable(value = "id") String utilisateurId)
		 throws ResourceNotFoundException{
			 Utilisateur utilisateur = UtilisateurRepository.findById(utilisateurId)
					 .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé pour cet id ::" + utilisateurId));
			 
			 UtilisateurRepository.delete(utilisateur);
			 Map < String, Boolean > response = new HashMap < > ();
			 response.put("deleted", Boolean.TRUE);
			 return response;

	 }
	 
	
}
 