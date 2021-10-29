package com.housservice.housstock.service;

import java.util.List;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import java.util.Optional;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;

public interface ClientService {

	public List<Client> findClientActif();
	
	public List<Client> findClientNotActif();

    Optional<Client> getClientById(String id);
	
	ClientDto buildClientDtoFromClient(Client client);
	
	void deleteClient(Client client);
	
	void createNewClient(@Valid ClientDto clientDto);
	
	void updateClient(@Valid ClientDto clientDto) throws ResourceNotFoundException;

}
