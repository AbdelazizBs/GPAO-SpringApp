package com.housservice.housstock.service;

import java.util.List;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.frontend.ClientFrontend;

public interface ClientService {

	public Client createClient(Client client);
	
	public List<Client> findClientActif();
	
	public List<Client> findClientNotActif();
	
	public List<ClientFrontend> findClientActif_Houss();
}
