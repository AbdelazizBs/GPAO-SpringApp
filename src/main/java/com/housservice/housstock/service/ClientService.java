package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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


	
	  void createNewClient(  String refClientIris,
							 String raisonSociale,
							 String adresse,
							 String codePostal,
							 String ville,
							 String pays,
							 String region,
							  String phone,
							 String email,
							 String statut,
							 String brancheActivite,
							 String secteurActivite,
							 String incoterm,
							 String echeance,
							 String modePaiement,
							 String nomBanque,
							 String adresseBanque,
							 String codeDouane,
							 String rne,
							 String cif,
							 String telecopie,
							 String rib,
							 String swift,
							  MultipartFile cdImage,
							  MultipartFile rnImage,
							  MultipartFile ciImage) throws ResourceNotFoundException;

	public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);

	public void updateClient(String idClient ,ClientDto clientDto) throws ResourceNotFoundException;
	public void miseEnVeille(String idClient ) throws ResourceNotFoundException;
	public void addContactClient( ContactDto contactDto, String idClient ) throws ResourceNotFoundException;
	public void updateContactClient( ContactDto contact, String idContact) throws ResourceNotFoundException;

	public void deleteClient(Client client);
	void deleteClientSelected(List<String> idClientsSelected);

	public void deleteContactClient(String idContact) throws ResourceNotFoundException;

}
