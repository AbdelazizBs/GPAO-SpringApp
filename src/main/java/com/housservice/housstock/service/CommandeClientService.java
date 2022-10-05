package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;

public interface CommandeClientService {
	
	public List<CommandeClientDto> getAllCommandeClientNonFermer();
	public List<CommandeClientDto> getAllCommandeClientFermer();

    public CommandeClientDto getCommandeClientById(String id);
	
    public CommandeClientDto buildCommandeClientDtoFromCommandeClient(CommandeClient commandeClient);
	
    public void createNewCommandeClient(@Valid CommandeClientDto commandeClientDto);
	
    public void updateCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
    public void fermeCmd(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException;

    public void deleteCommandeClient(String commandeClientId);

}
