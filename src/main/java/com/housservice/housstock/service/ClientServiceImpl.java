package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ClientMapper;
import com.housservice.housstock.mapper.ContactMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.ContactRepository;
import com.housservice.housstock.repository.PictureRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	
	final
	PictureRepository pictureRepository;


	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final  ContactRepository contactRepository ;


	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository,
							 MessageHttpErrorProperties messageHttpErrorProperties, ContactRepository contactRepository,
							 PictureRepository pictureRepository) {
		this.clientRepository = clientRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.contactRepository = contactRepository;
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
	public void Restaurer(String id) throws ResourceNotFoundException {
		System.out.println(id);
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
		client.setMiseEnVeille(false);
		clientRepository.save(client);
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
								 MultipartFile[] images) {
		if (clientRepository.existsClientByRefClientIris(refClientIris)) {
			throw new IllegalArgumentException(	"Matricule existe deja !!");
		}
		if (clientRepository.existsClientByRaisonSocial(raisonSociale)) {
			throw new IllegalArgumentException(	"Raison sociale existe deja !!");
		}
			ClientDto clientDto = new ClientDto();
			List<Picture> pictures = new ArrayList<>();
		for (MultipartFile file : images) {
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

	clientDto.setPictures(pictures);
	clientDto.setDate(new Date());
	clientDto.setMiseEnVeille(false);
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
	public void miseEnVeille(String idClient) throws ResourceNotFoundException {
		Client client = clientRepository.findById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
		client.setMiseEnVeille(true);
		clientRepository.save(client);
	}

	@Override
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
							 MultipartFile[] images) throws ResourceNotFoundException {

		if (!Objects.equals(email, "") && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Email invalide !!");
		}
		if (refClientIris.isEmpty() || raisonSociale.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty() || pays.isEmpty() || region.isEmpty()) {
			throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
		}
		Client client = getClientById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idClient)));
		if (!client.getRefClientIris().equals(refClientIris)) {
			throw new IllegalArgumentException("Error Id!!");
		}
		if (!client.getRaisonSocial().equals(raisonSociale)) {
			throw new IllegalArgumentException("Error Id!!");
		}
		List<Picture> pictures = new ArrayList<>();
		if (images != null) {
			for (MultipartFile file : images) {
				Picture picture = new Picture();
				picture.setFileName(file.getOriginalFilename());
				picture.setType(file.getContentType());
				try {
					picture.setBytes(file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				pictures.add(picture);
				pictureRepository.save(picture);
			}
		}
		pictures.addAll(client.getPictures());
		client.setPictures(pictures);
		client.setRaisonSocial(raisonSociale);
		client.setStatut(statut);
		client.setDate(client.getDate());
		client.setDateMiseEnVeille(client.getDateMiseEnVeille());
		client.setAdresse(adresse);
		client.setIncoterm(incoterm);
		client.setEcheance(echeance);
		client.setModePaiement(modePaiement);
		client.setNomBanque(nomBanque);
		client.setAdresseBanque(adresseBanque);
		client.setRib(rib);
		client.setSwift(swift);
		client.setBrancheActivite(brancheActivite);
		client.setSecteurActivite(secteurActivite);
		client.setRefClientIris(refClientIris);
		client.setTelecopie(telecopie);
		client.setPhone(phone);
		client.setStatut(statut);
		client.setCif(cif);
		client.setCodePostal(codePostal);
		client.setVille(ville);
		client.setPays(pays);
		client.setRne(rne);
		client.setCodeDouane(codeDouane);
		client.setRegion(region);
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
	public ResponseEntity<Map<String, Object>>  getIdClients(String raisonSociale) throws ResourceNotFoundException {
		Client client = clientRepository.findClientByRaisonSocial(raisonSociale).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),raisonSociale)));
		Map<String, Object> response = new HashMap<>();
		response.put("idClient", client.getId());
		response.put("refClient", client.getRefClientIris());
		return ResponseEntity.ok(response);
	}

	@Override
	public List<String> getRaisonSociales()   {
		List<Client> clients = clientRepository.findAll();
		return clients.stream()
				.map(Client::getRaisonSocial)
				.collect(Collectors.toList());
	}


	@Override
	public ResponseEntity<Map<String, Object>> getActiveClient(int page, int size) {
		try {
			List<ClientDto> clients = new ArrayList<ClientDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts =  clientRepository.findClientByMiseEnVeille(paging, false);
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
	public ResponseEntity<Map<String, Object>> onSortActiveClient(int page, int size, String field, String order) {
		try {
			List<ClientDto> clientDtos ;
			Page<Client> pageTuts;
			if (order.equals("1")){
				pageTuts = clientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
			}
			else {
				pageTuts = clientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
			}
			clientDtos = pageTuts.getContent().stream().map(client -> {
				return ClientMapper.MAPPER.toClientDto(client);
			}).collect(Collectors.toList());
			clientDtos =clientDtos.stream().filter(client -> !client.isMiseEnVeille()).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("clients", clientDtos);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Override
	public ResponseEntity<Map<String, Object>> onSortClientNotActive(int page, int size, String field, String order) {
		try {
			List<ClientDto> clientDtos ;
			Page<Client> pageTuts;
			if (order.equals("1")){
				pageTuts = clientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
			}
			else {
				pageTuts = clientRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
			}
			clientDtos = pageTuts.getContent().stream().map(client -> {
				return ClientMapper.MAPPER.toClientDto(client);
			}).collect(Collectors.toList());
			clientDtos =clientDtos.stream().filter(ClientDto::isMiseEnVeille).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("clients", clientDtos);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}





	@Override
	public ResponseEntity<Map<String, Object>> getClientNotActive(int page, int size) {
		try {
			List<ClientDto> clients = new ArrayList<ClientDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts =  clientRepository.findClientByMiseEnVeille(paging, true);
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

	@Override
	public void removePictures(String idClient) throws ResourceNotFoundException {
		Client client = clientRepository.findById(idClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
		for (String id : client.getPictures().stream().map(Picture::getId).collect(Collectors.toList())) {
			pictureRepository.deleteById(id);
		}
		client.getPictures().removeAll(client.getPictures());
		clientRepository.save(client);
	}
	@Override
	public void removePicture(String idPic) throws ResourceNotFoundException {
		Picture picture = pictureRepository.findById(idPic)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPic)));
		Client client = clientRepository.findClientByPictures(picture)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), picture)));
		pictureRepository.deleteById(idPic);
		client.getPictures().removeIf(picture1 -> picture1.equals(picture));
		clientRepository.save(client);
	}


	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

		try {

			List<ClientDto> clients;
			Pageable paging = PageRequest.of(page, size);
			Page<Client> pageTuts;
			pageTuts = clientRepository.findClientByTextToFind(textToFind, paging);
			clients = pageTuts.getContent().stream().map(client -> {
				return ClientMapper.MAPPER.toClientDto(client);
			}).collect(Collectors.toList());
			clients= clients.stream().filter(client -> client.isMiseEnVeille()==enVeille).collect(Collectors.toList());
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
	public ResponseEntity<byte[]> RecordReport(String refClientIris) {
		try{
			List<Client> client= clientRepository.findByrefClientIris(refClientIris);
			File file = ResourceUtils.getFile("classpath:Clients.jrxml");
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(client);
			Map<String ,Object> parameter = new HashMap<>();
			parameter.put("CreatedBy","Hellotest");
			JasperPrint print = JasperFillManager.fillReport(report, parameter,dataSource);
			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "employees-details.pdf");
			//create the report in PDF format
			return new ResponseEntity<byte[]>
					(JasperExportManager.exportReportToPdf(print), headers, HttpStatus.OK);

		} catch(Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public int getClientByMonth() {
		try {
			LocalDate today = LocalDate.now();
			ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalDate startD = LocalDate.now().withDayOfMonth(1);
			Date Firstday = Date.from(startD.atStartOfDay(defaultZoneId).toInstant());
			LocalDate endD = LocalDate.now().withDayOfMonth(today.getMonth().length(today.isLeapYear()));;
			Date Lastday = Date.from(endD.atStartOfDay(defaultZoneId).toInstant());
			List<Client> client = clientRepository.findBydateBetween(Firstday, Lastday);
			return (int) client.stream().count();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	@Override
	public int getallClient() {
		try {
			List<Client> client = clientRepository.findAll();
			return (int) client.stream().count();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<Integer> getClientListe(boolean b) {
		int date;

		List<Integer> nbClients = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

		List<Client> activeClients =clientRepository.findClientByMiseEnVeille(b);
		for(int i = 0; i< activeClients.size(); i++){
			Client client= activeClients.get(i);
			date=client.getDate().getMonth()+1;
			System.out.println(date);
			switch (date){
				case 9:
					nbClients .set(0, nbClients .get(0) + 1);
					break;
				case 10:
					nbClients .set(1, nbClients .get(1) + 1);
					break;
				case 11:
					nbClients .set(2, nbClients .get(2) + 1);
					break;
				case 12:
					nbClients .set(3, nbClients .get(3) + 1);
					break;
				case 1:
					nbClients .set(4, nbClients .get(4) + 1);
					break;
				case 2:
					nbClients .set(5, nbClients .get(5) + 1);
					break;
				case 3:
					nbClients .set(6, nbClients .get(6) + 1);
					break;
			}
		}
		return nbClients ;
	}

}
