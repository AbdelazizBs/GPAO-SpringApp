package com.housservice.housstock.controller.client;

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
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.service.SequenceGeneratorService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ClientController {
	
	@Autowired
	 private ClientRepository ClientRepository;
	
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	  
	 
	 @GetMapping("/client")
	 public List< Client > getAllClient() {
		 return ClientRepository.findAll();
		 
	 }
	  	 
		
	@GetMapping("/client/{id}") 
	public ResponseEntity < Client > getClientById(@PathVariable(value = "id") String clientId) throws
		  ResourceNotFoundException { Client client =
		  ClientRepository.findById(clientId) .orElseThrow(() -> new
		  ResourceNotFoundException("Client non trouvé pour cet id : " + clientId));
		  return ResponseEntity.ok().body(client); }
		 
	 
	   
	  @PutMapping("/client")
	  public Client createClient(@Valid @RequestBody Client client)
	  {
		  client.setId("" + sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME));
		  
		  // date
		  
		  // branche activité
		  
		  // secteur activité
		  
		  if (client.getListCommandes() != null)
		  {
			for (CommandeClient commandeClient : client.getListCommandes())
			{
				commandeClient.setId(client.getId() + "-" + sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME));
			}
		  }
	      return ClientRepository.save(client);
	  }
	
	 
	 @PutMapping("/client/{id}")
	 public ResponseEntity < Client > updateClient (@PathVariable(value = "id")String clientId,
			 @Valid @RequestBody Client clientData) throws ResourceNotFoundException {
		 Client client = ClientRepository.findById(clientId).orElseThrow(()-> new ResourceNotFoundException("Client non trouvé pour cet id :: " + clientId));
		 
		 client.setId(clientData.getId());
		 
		 client.setRaison_social(clientData.getRaison_social());
		 client.setRegime(clientData.getRegime());
		 client.setAdresse_facturation(clientData.getAdresse_facturation());
		 client.setAdresse_liv(clientData.getAdresse_liv());
		 client.setIncoterm(clientData.getIncoterm());
		 client.setEcheance(clientData.getEcheance());
		 client.setMode_pai(clientData.getMode_pai());
		 client.setNom_banque(clientData.getNom_banque());
		 client.setAdresse_banque(clientData.getAdresse_banque());
		 client.setRIB(clientData.getRIB());
		 client.setSWIFT(clientData.getSWIFT());
		 client.setBrancheActivite(clientData.getBrancheActivite());
		 client.setSecteurActivite(clientData.getSecteurActivite());
		 
		 
		 for (CommandeClient commandeClient : clientData.getListCommandes()) 
	      {
			if (StringUtils.isEmpty(commandeClient.getId()))
			{
				commandeClient.setId(client.getId() + "-" + sequenceGeneratorService.generateSequence(CommandeClient.SEQUENCE_NAME));
			}
	      }
	      
		  client.setListCommandes(clientData.getListCommandes());
		 
	     
		 final Client updateClient = ClientRepository.save(client);
		 return ResponseEntity.ok(updateClient);
		 	 
	 }
	 
	 @DeleteMapping("/client/{id}")
	public Map <String , Boolean> deleteClient(@PathVariable(value = "id") String clientId)
		 throws ResourceNotFoundException{
			 Client client = ClientRepository.findById(clientId)
					 .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé pour cet id ::" + clientId));
			 
			 ClientRepository.delete(client);
			 Map < String, Boolean > response = new HashMap < > ();
			 response.put("deleted", Boolean.TRUE);
			 return response;

	 }
	
}
