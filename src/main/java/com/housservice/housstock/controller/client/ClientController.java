package com.housservice.housstock.controller.client;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.service.ClientService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Clients Management"})
public class ClientController {
		
	@Autowired 
	private ClientService clientService;
	
	  private final MessageHttpErrorProperties messageHttpErrorProperties;
		
	
	  @Autowired
	  public ClientController(ClientService clientService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.clientService = clientService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

	 
	 @GetMapping("/client")
	 public List< Client > getAllClient() {
		 		
		 return clientService.findClientActif();
	 
	 }
	 
	 @GetMapping("/clientEnVeille")
	 public List< Client > getClientEnVeille() {
		 return clientService.findClientNotActif();
		 
	 }

	  @GetMapping("/client/{id}")
	  @ApiOperation(value = "service to get one Client by Id.")
	  public ResponseEntity < Client > getClientById(
			  @ApiParam(name = "id", value="id of client", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String clientId)
	  throws ResourceNotFoundException {
		  Client client = clientService.getClientById(clientId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(), clientId)));
	      return ResponseEntity.ok().body(client);
	  }

	   
		/*
		 * @PutMapping("/client") public Client createClient(@Valid @RequestBody Client
		 * client) { client.setId("" +
		 * sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME));
		 * 
		 * if (client.getListCommandes() != null) { for (CommandeClient commandeClient :
		 * client.getListCommandes()) { commandeClient.setId(client.getId() + "-" +
		 * sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME)); } } return
		 * clientService.createClient(client);
		 * 
		 * }
		 */
	  
	  
	  @PutMapping("/client")
	  public ResponseEntity<String> createClient(@Valid @RequestBody ClientDto clientDto) {
		  
		  clientService.createNewClient(clientDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getErro0003());
	  }
	  
	  
	 
	  @PutMapping("/client/{id}")
	  public ResponseEntity <String> updateClient(
			  @ApiParam(name = "id", value="id of client", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String clientId,
	      @Valid @RequestBody(required = true) ClientDto clientDto) throws ResourceNotFoundException {
		  
		  clientService.updateClient(clientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getErro0004());
	  }
	 
	  @DeleteMapping("/client/{id}")
	  @ApiOperation(value = "service to delete one Client by Id.")
	  public Map < String, Boolean > deleteclient(
			  @ApiParam(name = "id", value="id of client", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String clientId)
	  throws ResourceNotFoundException {
	      Client client = clientService.getClientById(clientId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(), clientId)));

	      clientService.deleteClient(client);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	 
	 
	 
	 
	
}
