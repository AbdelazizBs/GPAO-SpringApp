package com.housservice.housstock.service;

import java.util.List;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import java.util.Optional;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;

public interface ClientService {

	public List<Client> findClientActif();
	public String getIdClients(String raisonSociale) throws ResourceNotFoundException;

	public List<Client> findClientNotActif();
	public List<String> getRaisonSociales();

    Optional<Client> getClientById(String id);

	public ClientDto buildClientDtoFromClient(Client client);

	public void createNewClient(@Valid ClientDto clientDto);

	public void updateClient(@Valid ClientDto clientDto) throws ResourceNotFoundException;

	public void deleteClient(Client client);

}
