package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Map;

public interface CommandeClientService {
	

    ResponseEntity<Map<String, Object>> getAllCommandeClientNonFermer(int page , int size);
    ResponseEntity<Map<String, Object>> getAllCommandeClientFermer(int page , int size);

    public CommandeClientDto getCommandeClientById(String id);
	
    public CommandeClientDto buildCommandeClientDtoFromCommandeClient(CommandeClient commandeClient);
	
    public void createNewCommandeClient(CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
	
    public void updateCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
    public void fermeCmd(@Valid String idCmd) throws ResourceNotFoundException;

    public void deleteCommandeClient(String commandeClientId);

}
