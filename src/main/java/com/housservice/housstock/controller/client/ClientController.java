package com.housservice.housstock.controller.client;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.Client;

import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/client")
@Api(tags = {"Clients Management"})
public class ClientController {
		
	  private final ClientService clientService;
	
	  private final MessageHttpErrorProperties messageHttpErrorProperties;
		
	  @Autowired
	  public ClientController(ClientService clientService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.clientService = clientService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

		@GetMapping("/getAllClient")
		@ApiOperation(value = "service to get tout les clients ")
		public ResponseEntity<Map<String, Object>> getAllClient(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

			return clientService.findClientActif(page,size);

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

	  
	@GetMapping("/getIdClients/{raisonSociale}")
	@ApiOperation(value = "service to get Id Client by raisonSociale.")

	public String getIdClients(  @ApiParam(name = "raisonSociale", value="raisonSociale of clients", required = true)
								 @PathVariable(value = "raisonSociale", required = true) @NotEmpty(message = "{http.error.0001}") String raisonSociale) throws ResourceNotFoundException {
		return clientService.getIdClients(raisonSociale);
	}
	
	@GetMapping("/getRaisonSociales")
	@ApiOperation(value = "service to get one Raison Sociale")
	public List<String> getRaisonSociales() {
		return clientService.getRaisonSociales();
	}
	


	@GetMapping("/getAllClientNonActive")
	public ResponseEntity<Map<String, Object>> getAllClientNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return clientService.findClientNonActive(page,size);

	}


		@GetMapping("/search")
		@ApiOperation(value = "service to filter clients ")
		public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
		@RequestParam boolean enVeille,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "3") int size) {
			return clientService.find(textToFind, page, size,enVeille);

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


	  
		@PutMapping("/createNewClient")
		@Validated
		  public ResponseEntity<String> createNewClient(@Valid final String refClientIris ,
														  final String raisonSocial ,
														  final String regime ,
														  final String secteurActivite,
														  final String brancheActivite ,
														  final String adresseFacturation,
														  final String adresseLivraison,
														  final String incoterm,
														  final String telecopie,
														  final String phone,
														  final String echeance,
														  final String modePaiement,
														  final String nomBanque,
														  final String adresseBanque,
														  final String rib,
														  final String swift,
														  final String email
														  ) throws ResourceNotFoundException {

			  if(refClientIris.equals("undefined") || raisonSocial.equals("undefined") || regime.equals("undefined") || secteurActivite.equals("undefined") || brancheActivite.equals("undefined") ||
					  adresseFacturation.equals("undefined") || adresseLivraison.equals("undefined")|| incoterm.equals("undefined") || telecopie.equals("undefined") || phone.equals("undefined") || echeance.equals("undefined")
					  || modePaiement.equals("undefined")|| nomBanque.equals("undefined") || adresseBanque.equals("undefined") || rib.equals("undefined") || swift.equals("undefined")|| email.equals("undefined"))
			  {
				  throw new IllegalArgumentException("Voulez vous remplir le formulaire !");
			  }

	    	  clientService.createNewClient(refClientIris,
	    			      raisonSocial,
	    	              telecopie,
	    	              phone,
	    	              regime,
	    	              secteurActivite,
	    	              brancheActivite,
	    	              adresseFacturation,
	    	              adresseLivraison,
	    	              incoterm,
	    	              echeance,
	    	              modePaiement,
	    	              nomBanque,
	    	              adresseBanque,
	    	              rib,
	    	              swift,
	    	              email	
					  );
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		  }
	  
	   
	  @PutMapping("/updateClient/{idClient}")
	  public ResponseEntity <String> updateClient(
			  @ApiParam(name = "idClient", value="id of client", required = true)
			  @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idClient,
	       @RequestBody(required = true) ClientDto clientDto) throws ResourceNotFoundException {
		  
		  clientService.updateClient(idClient,clientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
	  @PutMapping("/miseEnVeille/{idClient}")
	  public ResponseEntity <String> miseEnVeille(
			  @ApiParam(name = "idClient", value="id of client", required = true)
			  @PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}")  String idClient,
			  @RequestBody(required = true) ClientDto clientDto) throws ResourceNotFoundException {

		  clientService.miseEnVeille(idClient);

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
	public Map< String, Boolean > deleteContactClient(
			@ApiParam(name = "idContact", value="id of client", required = true)
			@PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
			throws ResourceNotFoundException {
		clientService.deleteContactClient(idContact);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	 
	
}
