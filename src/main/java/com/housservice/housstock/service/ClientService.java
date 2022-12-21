package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

	ResponseEntity<Map<String, Object>> findClientActif(int page , int size);
	ResponseEntity<Map<String, Object>> findClientNonActive(int page , int size);

	public String getIdClients(String raisonSociale) throws ResourceNotFoundException;

	public List<String> getRaisonSociales();

    Optional<Client> getClientById(String id);
    List<Article> getArticles(String id) throws ResourceNotFoundException;
    List<Article> getArticlesByRaisons(String raison) throws ResourceNotFoundException;


	
	  void createNewClient(ClientDto clientDto) throws ResourceNotFoundException;

	public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);

	public void updateClient(String idClient ,ClientDto clientDto) throws ResourceNotFoundException;
	public void miseEnVeille(String idClient ) throws ResourceNotFoundException;
	public void addContactClient( ContactDto contactDto, String idClient ) throws ResourceNotFoundException;
	public void updateContactClient( ContactDto contact, String idContact) throws ResourceNotFoundException;

	public void deleteClient(Client client);
	void deleteClientSelected(List<String> idClientsSelected);

	public void deleteContactClient(String idContact) throws ResourceNotFoundException;

}
