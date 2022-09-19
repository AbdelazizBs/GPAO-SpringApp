package com.housservice.housstock.controller.client;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.dto.ArticleDto;
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
@RequestMapping("/api/v1/client")
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

	@GetMapping("/getIdClients/{raisonSociale}")
	@ApiOperation(value = "service to get one Commande Client by Id.")

	public String getIdClients(  @ApiParam(name = "raisonSociale", value="raisonSociale of clients", required = true)
								 @PathVariable(value = "raisonSociale", required = true) @NotEmpty(message = "{http.error.0001}") String raisonSociale) throws ResourceNotFoundException {
		return clientService.getIdClients(raisonSociale);

	}
	@GetMapping("/getRaisonSociales")
	@ApiOperation(value = "service to get one Commande Client by Id.")
	public List<String> getIdClients() {
		return clientService.getRaisonSociales();

	}
	 @GetMapping("/getAllClient")
	 public List< Client > getAllClient() {
		 		
		 return clientService.findClientActif();
	 
	 }

	 @GetMapping("/getAllClientNonActive")
	 public List< Client > getAllClientNonActive() {
		 return clientService.findClientNonActive();

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
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), clientId)));
	      return ResponseEntity.ok().body(client);
	  }


	  @GetMapping("/getArticles/{idClient}")
	  @ApiOperation(value = "service to get one Client by Id.")
	  public List <Article> getArticles(
			  @ApiParam(name = "idClient", value="id of client", required = true)
			  @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient)
	  throws ResourceNotFoundException {
		  return  clientService.getArticles(idClient) ;

	  }

	  @GetMapping("/getArticlesByRaisons/{raisonS}")
	  @ApiOperation(value = "service to get one Client by Id.")
	  public List <Article> getArticlesByRaisons(
			  @ApiParam(name = "raisonS", value="raison sociale of client", required = true)
			  @PathVariable(value = "raisonS", required = true) @NotEmpty(message = "{http.error.0001}") String raisonS)
	  throws ResourceNotFoundException {
		  return  clientService.getArticlesByRaisons(raisonS) ;
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
	  
	  
	  @PutMapping("/addClient")
	  public ResponseEntity<String> createClient(@Valid @RequestBody ClientDto clientDto) {
		  clientService.createNewClient(clientDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
	  
	   
	  @PutMapping("/updateClient/{idClient}")
	  public ResponseEntity <String> updateClient(
			  @ApiParam(name = "idClient", value="id of client", required = true)
			  @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idClient,
	      @Valid @RequestBody(required = true) ClientDto clientDto) throws ResourceNotFoundException {
		  
		  clientService.updateClient(clientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

	  @PutMapping("/addContactClient/{idClient}")
	  public ResponseEntity <String> addContactClient(
			  @ApiParam(name = "idClient", value="id of client", required = true)
			  @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idClient,
	      @Valid @RequestBody(required = true) Contact contact) throws ResourceNotFoundException {

		  clientService.addContactClient(contact,idClient);

	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

	  @PutMapping("/updateContactClient/{idContact}")
	  public ResponseEntity <String> updateContactClient(
			  @ApiParam(name = "idContact", value="id of contact", required = true)
			  @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}")  String idContact,
	      @Valid @RequestBody(required = true) Contact contact ) throws ResourceNotFoundException {
		  clientService.updateContactClient(contact,idContact);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
	 
	  @DeleteMapping("/deleteClient/{id}")
	  @ApiOperation(value = "service to delete one Client by Id.")
	  public Map < String, Boolean > deleteclient(
			  @ApiParam(name = "id", value="id of client", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String clientId)
	  throws ResourceNotFoundException {
	      Client client = clientService.getClientById(clientId)
	    		  .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), clientId)));

	      clientService.deleteClient(client);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

	  @DeleteMapping("/deleteContactClient/{idContact}")
	  @ApiOperation(value = "service to delete one Client by Id.")
	  public Map < String, Boolean > deleteContactClient(
			  @ApiParam(name = "idContact", value="id of client", required = true)
			  @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
	  throws ResourceNotFoundException {
	      clientService.deleteContactClient(idContact);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	 
	
}
