package com.housservice.housstock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.frontend.ClientFrontend;
import com.housservice.housstock.repository.ClientRepository;

public class ClientService {

	@Autowired
	 private ClientRepository clientRepository;
	
	public Client createClient (Client client)
	{
		return clientRepository.save(client);
	}


	 public List< Client > findClientActif() {
		 
		 return clientRepository.findClientActif();
		 // liste des clients frontend 
	 }
	
public List< Client > findClientNotActif() {
		 
		 return clientRepository.findClientNotActif();

	 }
	 
	public List<ClientFrontend> findClientActif_Houss()
	{
		List<Client> listClient = clientRepository.findClientActif();
		List<ClientFrontend>  listClientFront = new ArrayList<>();
		
		for (Client client : listClient) 
		{
			ClientFrontend clientFront = new ClientFrontend();
			// TODO getSecteurById
			//clientFront.setid
			listClientFront.add(clientFront);
		}
		return listClientFront;
	}
	
	
}
