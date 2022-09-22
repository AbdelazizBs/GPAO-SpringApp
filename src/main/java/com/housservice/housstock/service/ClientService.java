package com.housservice.housstock.service;

import java.util.List;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.ClientDto;
import java.util.Optional;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;

public interface ClientService {

	public List<Client> findClientActif();
	public List<Client> findClientNonActive();
	public String getIdClients(String raisonSociale) throws ResourceNotFoundException;

	public List<Client> findClientNotActif();
	public List<String> getRaisonSociales();

    Optional<Client> getClientById(String id);
    List<Article> getArticles(String id) throws ResourceNotFoundException;
    List<Article> getArticlesByRaisons(String raison) throws ResourceNotFoundException;

	public ClientDto buildClientDtoFromClient(Client client);

	public void createNewClient(@Valid ClientDto clientDto);

	public void updateClient(@Valid ClientDto clientDto) throws ResourceNotFoundException;
	public void addContactClient(@Valid Contact contact,String idClient ) throws ResourceNotFoundException;
	public void updateContactClient(@Valid Contact contact, String idContact) throws ResourceNotFoundException;

	public void deleteClient(Client client);

}
