package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ContactDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

	ResponseEntity<Map<String, Object>> getActiveClient(int page , int size);
	ResponseEntity<Map<String, Object>> getClientNotActive(int page , int size);

	public ResponseEntity<Map<String, Object>>  getIdClients(String raisonSociale) throws ResourceNotFoundException;

	public List<String> getRaisonSociales();

    Optional<Client> getClientById(String id);


	public ResponseEntity<Map<String, Object>> onSortActiveClient(int page, int size, String field, String order);
	public ResponseEntity<Map<String, Object>> onSortClientNotActive(int page, int size, String field, String order);

	
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
							  MultipartFile[] images) throws ResourceNotFoundException;
	public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);




	public void updateClient(String idClient ,String refClientIris,
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
							 MultipartFile[] images) throws ResourceNotFoundException;
	 void miseEnVeille(String idClient ) throws ResourceNotFoundException;
	 void addContactClient( ContactDto contactDto, String idClient ) throws ResourceNotFoundException;
	 void updateContactClient( ContactDto contact, String idContact) throws ResourceNotFoundException;

	 void deleteClient(Client client);
	void deleteClientSelected(List<String> idClientsSelected);

	 void deleteContactClient(String idContact) throws ResourceNotFoundException;
	 void removePictures(String idClient) throws ResourceNotFoundException;

	void removePicture(String idPicture) throws ResourceNotFoundException;
	public ResponseEntity<byte[]> RecordReport(String refClientIris);
}
