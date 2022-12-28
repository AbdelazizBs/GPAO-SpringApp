package com.housservice.housstock.controller.commandeClient;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.service.CommandeClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/commandeClient")
@Api(tags = {"Commandes Clients Management"})
public class CommandeClientController {

	private final CommandeClientService commandeClientService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
		
    @Autowired
	  public CommandeClientController(CommandeClientService commandeClientService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.commandeClientService = commandeClientService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
    


	@GetMapping("/getAllCommandeClientNonFermer")
	public ResponseEntity<Map<String, Object>> getAllCommandeClientNonFermer(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return commandeClientService.getAllCommandeClientNonFermer(page,size);

	}

	@GetMapping("/getAllCommandeClientFermer")
	public ResponseEntity<Map<String, Object>> getAllCommandeClientFermer(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return commandeClientService.getAllCommandeClientFermer(page,size);

	}





    
    @GetMapping("/commandeClient/{id}")
	  @ApiOperation(value = "service to get one Commande Client by Id.")
	  public ResponseEntity < CommandeClientDto > getCommandeClientById(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeClientId)
	  throws ResourceNotFoundException {
  	CommandeClientDto commandeClient = commandeClientService.getCommandeClientById(commandeClientId);
		  if (commandeClient == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(commandeClient);
	  }
    
    @PutMapping("/addCmdClient")
	  public ResponseEntity<String> createCommandeClient(@RequestBody CommandeClientDto commandeClientDto) {
		  
  	  commandeClientService.createNewCommandeClient(commandeClientDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());


	  }

    
    @PutMapping("/updateCommandeClient/{id}")
	  public ResponseEntity <String> updateCommandeClient(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String commandeClientId,
	          @Valid @RequestBody(required = true) CommandeClientDto commandeClientDto) throws ResourceNotFoundException {
		  
  	  commandeClientService.updateCommandeClient(commandeClientDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
        @PutMapping("/fermeCmd/{id}")
	  public ResponseEntity <String>fermeCmd(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String commandeClientId) throws ResourceNotFoundException {

			  commandeClientService.fermeCmd(commandeClientId);

			return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
    @DeleteMapping("/deleteCommandeClient/{id}")
	  @ApiOperation(value = "service to delete one Commande Client by Id.")
	  public Map < String, Boolean > deleteCommandeClient(
			  @ApiParam(name = "id", value="id of commandeClient", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String commandeClientId) {

		  commandeClientService.deleteCommandeClient(commandeClientId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
    
}
