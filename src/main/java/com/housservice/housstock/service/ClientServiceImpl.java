package com.housservice.housstock.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import javax.validation.Valid;

import com.housservice.housstock.model.Article;

import com.housservice.housstock.model.Contact;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private ClientRepository clientRepository;
	
	private final ArticleRepository articleRepository ;

	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final  ContactRepository contactRepository ;



	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, SequenceGeneratorService sequenceGeneratorService,
					MessageHttpErrorProperties messageHttpErrorProperties, ContactRepository contactRepository, ArticleRepository articleRepository) {
		this.clientRepository = clientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.contactRepository = contactRepository;
		this.articleRepository = articleRepository;
	}
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	@Override
	public ClientDto buildClientDtoFromClient(Client client) {
		if (client == null) {
			return null;
		}
		
		  ClientDto clientDto = new ClientDto(); clientDto.setId(client.getId());
		  
		  clientDto.setRaisonSocial(client.getRaisonSocial());
		  clientDto.setRegime(client.getRegime());
		  clientDto.setAdresseFacturation(client.getAdresseFacturation());
		  clientDto.setAdresseLivraison(client.getAdresseLivraison());
		  clientDto.setIncoterm(client.getIncoterm());
		  clientDto.setContact(client.getContact());
		  clientDto.setEcheance(client.getEcheance());
		  clientDto.setMiseEnVeille(client.getMiseEnVeille());
		  clientDto.setModePaiement(client.getModePaiement());
		  clientDto.setNomBanque(client.getNomBanque());
		  clientDto.setAdresseBanque(client.getAdresseBanque());
		  clientDto.setRib(client.getRib()); clientDto.setSwift(client.getSwift());
		  clientDto.setBrancheActivite(client.getBrancheActivite());
		  clientDto.setSecteurActivite(client.getSecteurActivite());
		 

		return clientDto;
	}

	
	@Override
	public Optional<Client> getClientById(String clientId) {
		return clientRepository.findById(clientId);
	}


	@Override
	public List<Article> getArticles(String clientId) throws ResourceNotFoundException {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), clientId)));
		List<Article> articles = articleRepository.findArticleByClientId(client.getId());
		return articles;
	}
	@Override
	public List<Article> getArticlesByRaisons(String raison) throws ResourceNotFoundException {
		Client client = clientRepository.findClientByRaisonSocial(raison)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), raison)));
		List<Article> articles = articleRepository.findArticleByClientId(client.getId());
//		articles.stream().map(article -> decompressBytes(article.getPicture().getBytes()));
		return articles;
	}

	
	@Override
	public void deleteClient(Client client) {
		clientRepository.delete(client);
		
	}

	@Override
	public void createNewClient(@Valid ClientDto clientDto) {
clientDto.setDate( LocalDate.now());
clientDto.setMiseEnVeille(0);
List<Contact> contacts = new ArrayList<>();
if (clientDto.getContact()==null){
	clientDto.setContact(contacts);
}
clientRepository.save(buildClientFromClientDto(clientDto));
	}
	
	
	private Client buildClientFromClientDto(ClientDto clientDto) {
		Client client = new Client();
		client.setId(""+sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME));
		client.setRaisonSocial(clientDto.getRaisonSocial());		
		client.setRegime(clientDto.getRegime());
		client.setContact(clientDto.getContact());
		client.setAdresseFacturation(clientDto.getAdresseFacturation());
		client.setAdresseLivraison(clientDto.getAdresseLivraison());
		client.setIncoterm(clientDto.getIncoterm());
		client.setMiseEnVeille(clientDto.getMiseEnVeille());
		client.setEcheance(clientDto.getEcheance());
		client.setModePaiement(clientDto.getModePaiement());
		client.setNomBanque(clientDto.getNomBanque());
		client.setAdresseBanque(clientDto.getAdresseBanque());
		client.setRib(clientDto.getRib());
		client.setSwift(clientDto.getSwift());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());
		
		return client;
	
		}


	@Override
	public void updateClient(@Valid ClientDto clientDto ) throws ResourceNotFoundException {
		Client client = getClientById(clientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  clientDto.getId())));
		client.setRaisonSocial(clientDto.getRaisonSocial());		
		client.setRegime(clientDto.getRegime());
		client.setMiseEnVeille(clientDto.getMiseEnVeille());
		client.setDate(client.getDate());
		client.setDateMiseEnVeille(client.getDateMiseEnVeille());
		client.setAdresseFacturation(clientDto.getAdresseFacturation());
		client.setAdresseLivraison(clientDto.getAdresseLivraison());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setModePaiement(clientDto.getModePaiement());
		client.setNomBanque(clientDto.getNomBanque());
		client.setAdresseBanque(clientDto.getAdresseBanque());
		client.setRib(clientDto.getRib());
		client.setSwift(clientDto.getSwift());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());
		clientRepository.save(client);
		
	}

	@Override
	public void addContactClient(@Valid Contact contact ,String idClient ) throws ResourceNotFoundException {
		Client client = getClientById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idClient)));
		Contact contact1 = new Contact();
		List<Contact> contacts = new ArrayList<>();
		if(client.getContact()==null){
			contact1.setNom(contact.getNom());
			contact1.setEmail(contact.getEmail());
			contact1.setMobile(contact.getMobile());
			contact1.setAddress(contact.getAddress());
			contact1.setFonction(contact.getFonction());
			contact1.setPhone(contact.getPhone());
			contacts.add(contact1);
			contactRepository.save(contact);
			client.setContact(contacts);
			clientRepository.save(client);
		}
		contactRepository.save(contact);
		contacts.add(contact);
		contacts.addAll(client.getContact());
		client.setContact(contacts);
		clientRepository.save(client);

	}

	@Override
	public void updateContactClient(@Valid Contact contact,String idContact) throws ResourceNotFoundException {
		Client client =clientRepository.findClientByContactId(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contact)));
		Contact contactToUpdate = contactRepository.findById(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contact.getId())));
		client.getContact().removeIf(contact1 -> contact1.equals(contactToUpdate));
		contactToUpdate.setNom(contact.getNom());
		contactToUpdate.setEmail(contact.getEmail());
		contactToUpdate.setMobile(contact.getMobile());
		contactToUpdate.setAddress(contact.getAddress());
		contactToUpdate.setFonction(contact.getFonction());
		contactToUpdate.setPhone(contact.getPhone());
		contactRepository.save(contactToUpdate);
		client.getContact().add(contactToUpdate);
		clientRepository.save(client);

	}
	@Override
	public String getIdClients(String raisonSociale) throws ResourceNotFoundException {
		Client client = clientRepository.findClientByRaisonSocial(raisonSociale).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),raisonSociale)));
		return client.getId() ;
	}

	@Override
	public List<String> getRaisonSociales( )   {
		List<Client> clients = clientRepository.findAll();
		return clients.stream()
				.map(Client::getRaisonSocial)
				.collect(Collectors.toList());
	}


	@Override
	public ResponseEntity<Map<String, Object>> findClientActif(int page, int size) {
		try {
			List<ClientDto> clients = new ArrayList<ClientDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts =  clientRepository.findClientActif(paging);
			clients = pageTuts.getContent().stream().map(client -> buildClientDtoFromClient(client)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("clients", clients);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> findClientNonActive(int page, int size) {
		try {
			List<ClientDto> clients = new ArrayList<ClientDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts =  clientRepository.findClientNotActif(paging);
			clients = pageTuts.getContent().stream().map(client -> buildClientDtoFromClient(client)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("clients", clients);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}






	@Override
	public void deleteContactClient(String idContact) throws ResourceNotFoundException {
		Client client = clientRepository.findClientByContactId(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
		Contact contact = contactRepository.findById(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
		List<Contact> contactList = client.getContact();
		contactList.removeIf(c -> c.equals(contact));
		client.setContact(contactList);
		clientRepository.save(client);
		contactRepository.deleteById(idContact);
	}
}
