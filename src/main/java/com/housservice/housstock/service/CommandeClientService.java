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

     CommandeClientDto getCommandeClientById(String id);
	
     CommandeClientDto buildCommandeClientDtoFromCommandeClient(CommandeClient commandeClient);
	
     void createNewCommandeClient(CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
	
     void updateCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
     void fermeCmd(@Valid String idCmd) throws ResourceNotFoundException;

     void deleteCommandeClient(String commandeClientId);
}
