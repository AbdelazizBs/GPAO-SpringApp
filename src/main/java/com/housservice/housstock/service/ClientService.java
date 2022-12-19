package com.housservice.housstock.service;

import java.util.List;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.dto.ClientDto;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
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


	
	 public void createNewClient(String refClientIris,
             String raisonSocial,
             String telecopie,
             String phone,
             String regime,
             String secteurActivite,
             String brancheActivite,
             String adresseFacturation,
             String adresseLivraison,
             String incoterm,
             String echeance,
             String modePaiement,
             String nomBanque,
             String adresseBanque,
             String rib,
             String swift,
             String email
      
             ) throws ResourceNotFoundException;

	public ResponseEntity<Map<String, Object>> find(String textToFind,int page, int size,boolean enVeille);

	public void updateClient(String idClient ,@Valid ClientDto clientDto) throws ResourceNotFoundException;
	public void miseEnVeille(String idClient ) throws ResourceNotFoundException;
	public void addContactClient(@Valid Contact contact,String idClient ) throws ResourceNotFoundException;
	public void updateContactClient(@Valid Contact contact, String idContact) throws ResourceNotFoundException;

	public void deleteClient(Client client);
	public void deleteContactClient(String idContact) throws ResourceNotFoundException;

}
