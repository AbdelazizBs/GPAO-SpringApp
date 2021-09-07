package com.housservice.housstock.controller.commandeClient;

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
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CommandeClientController {

	@Autowired
	 private CommandeClientRepository commandeClientRepository;
	
	@Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/commandeClient")
	 public List<CommandeClient> getAllCommandeClient() {
		 return commandeClientRepository.findAll();
		 
	 }
	
	
	 @PutMapping("/commandeClient")
	 public CommandeClient createCommandeClient(@Valid @RequestBody CommandeClient commandeClient)
	 {
		 commandeClient.setId("" + sequenceGeneratorService.generateSequence(CommandeClient.SEQUENCE_NAME));
		 return commandeClientRepository.save(commandeClient);
	 }
	
	
	 @PutMapping("/commandeClient/{id}")
	 public ResponseEntity < CommandeClient > updateCommandeClient (@PathVariable(value = "id")String commandeClientId,
			 @Valid @RequestBody CommandeClient commandeClientData) throws ResourceNotFoundException {
		 CommandeClient commandeClient = commandeClientRepository.findById(commandeClientId).orElseThrow(()-> new ResourceNotFoundException("Commande Client non trouvé pour cet id : " + commandeClientId));
		 
		 commandeClient.setId(commandeClientData.getId());
		 commandeClient.setType_cmd(commandeClientData.getType_cmd());
		 commandeClient.setNum_cmd(commandeClientData.getNum_cmd());
		 commandeClient.setEtat(commandeClientData.getEtat());
		 commandeClient.setDate_cmd(commandeClientData.getDate_cmd());
		 commandeClient.setDate_creation_cmd(commandeClientData.getDate_creation_cmd());
		 final CommandeClient updateCommandeClient = commandeClientRepository.save(commandeClient);
		 return ResponseEntity.ok(updateCommandeClient);
	 }
	
	
	 @DeleteMapping("/commandeClient/{id}")
		public Map <String , Boolean> deleteCommandeClient(@PathVariable(value = "id") String commandeClientId)
			 throws ResourceNotFoundException{
		 CommandeClient commandeClient = commandeClientRepository.findById(commandeClientId)
						 .orElseThrow(() -> new ResourceNotFoundException("Commande Client non trouvé pour cet id :" + commandeClientId));
				 
		 commandeClientRepository.delete(commandeClient);
				 Map < String, Boolean > response = new HashMap < > ();
				 response.put("deleted", Boolean.TRUE);
				 return response;
		 }
	
	
	
}
