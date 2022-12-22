package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ClientMapper;
import com.housservice.housstock.mapper.ContactMapper;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.ContactRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	
	private final ArticleRepository articleRepository ;
	final
	PictureRepository pictureRepository;

	private final SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final  ContactRepository contactRepository ;



	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, SequenceGeneratorService sequenceGeneratorService,
							 MessageHttpErrorProperties messageHttpErrorProperties, ContactRepository contactRepository, ArticleRepository articleRepository, PictureRepository pictureRepository) {
		this.clientRepository = clientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.contactRepository = contactRepository;
		this.articleRepository = articleRepository;
		this.pictureRepository = pictureRepository;
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
	public void deleteClientSelected(List<String> idClientsSelected){
		for (String id : idClientsSelected){
			clientRepository.deleteById(id);
		}
	}

	@Override
	public void createNewClient( String refClientIris,
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
								 MultipartFile ciImage) {
		if (clientRepository.existsClientByRefClientIris(refClientIris)) {
			throw new IllegalArgumentException(	" Matricule " + refClientIris + "  existe deja !!");
		}
			ClientDto clientDto = new ClientDto();
			List<Picture> pictures = new ArrayList<>();
		for (MultipartFile file : Arrays.asList(cdImage, rnImage, ciImage)) {
			if(!Objects.equals(file.getOriginalFilename(), "fileNotSelected")){
			Picture picture = new Picture();
			picture.setFileName(file.getOriginalFilename());
			picture.setType(file.getContentType());
			try {
				picture.setBytes(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			pictureRepository.save(picture);
			pictures.add(picture);
		}
			}
	clientDto.setPictures(pictures);
	clientDto.setDate(new Date());
	clientDto.setMiseEnVeille(0);
	clientDto.setRefClientIris(refClientIris);
	clientDto.setRaisonSocial(raisonSociale);
	clientDto.setAdresse(adresse);
	clientDto.setCodePostal(codePostal);
	clientDto.setVille(ville);
	clientDto.setPays(pays);
	clientDto.setRegion(region);
	clientDto.setPhone(phone);
	clientDto.setEmail(email);
	clientDto.setStatut(statut);
	clientDto.setBrancheActivite(brancheActivite);
	clientDto.setSecteurActivite(secteurActivite);
	clientDto.setIncoterm(incoterm);
	clientDto.setEcheance(echeance);
	clientDto.setModePaiement(modePaiement);
	clientDto.setNomBanque(nomBanque);
	clientDto.setAdresseBanque(adresseBanque);
	clientDto.setCodeDouane(codeDouane);
	clientDto.setRne(rne);
	clientDto.setCif(cif);
	clientDto.setTelecopie(telecopie);
	clientDto.setRib(rib);
	clientDto.setSwift(swift);
	List<Contact> contacts = new ArrayList<>();
		if (clientDto.getContact()==null){
	clientDto.setContact(contacts);
}
		Client client = ClientMapper.MAPPER.toClient(clientDto);
		clientRepository.save(client);
	}

	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

		try {

			List<ClientDto> clients;
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts = clientRepository.findClientByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			clients = pageTuts.getContent().stream().map(client -> {
				return ClientMapper.MAPPER.toClientDto(client);
			}).collect(Collectors.toList());
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
	public void miseEnVeille(String idClient) throws ResourceNotFoundException {
		Client client = clientRepository.findById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
		client.setMiseEnVeille(1);
		clientRepository.save(client);
	}

	@Override
	public void updateClient(String idClient , ClientDto clientDto) throws ResourceNotFoundException {
		Client client = getClientById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  clientDto.getId())));
//		List<Picture> pictures = new ArrayList<>();
//		for (MultipartFile multipartFile : files) {
//			Picture picture = new Picture();
//			picture.setFileName(multipartFile.getOriginalFilename());
//			picture.setType(multipartFile.getContentType());
//			try {
//				picture.setBytes(multipartFile.getBytes());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			pictureRepository.save(picture);
//			pictures.add(picture);
//		}
		client.setRaisonSocial(clientDto.getRaisonSocial());
		client.setRegime(clientDto.getRegime());
		client.setMiseEnVeille(clientDto.getMiseEnVeille());
		client.setDate(client.getDate());
		client.setDateMiseEnVeille(client.getDateMiseEnVeille());
		client.setAdresse(clientDto.getAdresse());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setModePaiement(clientDto.getModePaiement());
		client.setNomBanque(clientDto.getNomBanque());
		client.setAdresseBanque(clientDto.getAdresseBanque());
		client.setRib(clientDto.getRib());
		client.setSwift(clientDto.getSwift());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());
		client.setRefClientIris(clientDto.getRefClientIris());
		client.setTelecopie(clientDto.getTelecopie());
		client.setPhone(clientDto.getPhone());
		client.setStatut(clientDto.getStatut());
		client.setCif(clientDto.getCif());
		client.setCodePostal(clientDto.getCodePostal());
		client.setVille(clientDto.getVille());
		client.setPays(clientDto.getPays());
		client.setRne(clientDto.getRne());
		client.setCodeDouane(clientDto.getCodeDouane());
		client.setRegion(clientDto.getRegion());
		clientRepository.save(client);
		
	}


	@Override
	public void addContactClient(@Valid ContactDto contactDto , String idClient ) throws ResourceNotFoundException {
		Client client = getClientById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idClient)));
		List<Contact> contacts = new ArrayList<>();
		Contact contact1 = ContactMapper.MAPPER.toContact(contactDto);
		if(client.getContact()==null){
			contacts.add(contact1);
			contactRepository.save(contact1);
			client.setContact(contacts);
			clientRepository.save(client);
		}
		contactRepository.save(contact1);
		contacts.add(contact1);
		contacts.addAll(client.getContact());
		client.setContact(contacts);
		clientRepository.save(client);

	}

	@Override
	public void updateContactClient(ContactDto contactDto,String idContact) throws ResourceNotFoundException {
		// update contact by id contact and update it in client contact list
//		Contact contact = contactRepository.findById(idContact)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idContact)));
//		contact.setNom(contactDto.getNom());
//		contact.setEmail(contactDto.getEmail());
//		contact.setMobile(contactDto.getMobile());
//		contact.setFonction(contactDto.getFonction());
//		contact.setPhone(contactDto.getPhone());
//		contactRepository.save(contact);

		Client client =clientRepository.findClientByContactId(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contactDto)));
		Contact contactToUpdate = contactRepository.findById(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contactDto.getId())));
		client.getContact().removeIf(contact1 -> contact1.equals(contactToUpdate));
		contactToUpdate.setNom(contactDto.getNom());
		contactToUpdate.setEmail(contactDto.getEmail());
		contactToUpdate.setMobile(contactDto.getMobile());
		contactToUpdate.setFonction(contactDto.getFonction());
		contactToUpdate.setPhone(contactDto.getPhone());
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
			clients = pageTuts.getContent().stream().map(client -> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
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
			clients = pageTuts.getContent().stream().map(client -> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
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
