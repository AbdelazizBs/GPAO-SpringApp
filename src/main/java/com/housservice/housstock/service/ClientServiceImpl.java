package com.housservice.housstock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.frontend.ClientFrontend;
import com.housservice.housstock.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

	private ClientRepository clientRepository;

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client createClient(Client client) {
		return clientRepository.save(client);
	}

	public List<Client> findClientActif() {

		return clientRepository.findClientActif();
		// liste des clients frontend
	}

	public List<Client> findClientNotActif() {

		return clientRepository.findClientNotActif();

	}

	public List<ClientFrontend> findClientActif_Houss() {
		List<Client> listClient = clientRepository.findClientActif();
		List<ClientFrontend> listClientFront = new ArrayList<>();

		for (Client client : listClient) {
			ClientFrontend clientFront = new ClientFrontend();
			// TODO getSecteurById
			// clientFront.setid
			listClientFront.add(clientFront);
		}
		return listClientFront;
	}

}
