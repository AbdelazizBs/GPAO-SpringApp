package com.housservice.housstock;

import com.housservice.housstock.mapper.ClientMapper;
import com.housservice.housstock.mapper.FournisseurMapper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.ContactRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.housservice.housstock.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;


import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class HousstockApplicationTests {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	PictureRepository pictureRepository;
	private ContactDto contactDto;

	@Test
	public void testCreateNewClient(){
		Client client = new Client(new Date(),"ABC456","Ma société","Régimeee","Automobile","Informatique","FOB","30 jours","Virement","0123456789","0123456798","Ma Banque","2 Rue des Banques","12345678901234567890123456","SWIFT123","contact@masociete.com",false,0);

		clientRepository.save(client);
	}
	@Test
	public void testGetClientById(){
		Client client=clientRepository.findById("63fbb9a06cdf695a8104adb2").get();
		System.out.println(client);
		//verification que  l'objet client n'est pas null
		assertNotNull(client);
		//verifier que le numero de telephone de premier contact de l'objet client  est egale aver la valeur expecteé  "123456789".
		assertEquals("123456789", client.getContact().get(0).getPhone());
	}
	@Test
	public void testUpdateClient(){
		/*String clientId = "63fb8ce9b114844bd801dfb4";
		String newPhone = "00123456";*/
		Client client=clientRepository.findById("64006599df102013ae97d348").get();
		if (client != null) {
			//client.setPhone("00123456");
			client.setSecteurActivite("Textile");
			clientRepository.save(client);
		}
		/*client.setPhone("123456");
		clientRepository.save(client);*/
		System.out.println(client);
		Client updatedClient = clientRepository.findById("64006599df102013ae97d348").orElse(null);
		assertNotNull(updatedClient);
		//assertEquals("00123456", updatedClient.getPhone());
		assertEquals("Textile", updatedClient.getSecteurActivite());
	}
	@Test
	public void testDeleteClient(){
		clientRepository.deleteById("63f73c2f5cfa3d46c96fe12b");
		//verifier que le client a ete supprime
		Optional<Client> deletedClient = clientRepository.findById("63f73c2f5cfa3d46c96fe12b");
		assertFalse(deletedClient.isPresent());
	}
	@Test
	public void testDeleteClientSelected(){
		List<String> idClientsSelected=new ArrayList<>();
		idClientsSelected.add("63fbb9a06cdf695a8104adb2");
		idClientsSelected.add("63fc9a9d912111525e44aa31");
		for (String id : idClientsSelected){
			clientRepository.deleteById(id);
		}
		Optional<Client> deletedClient1 = clientRepository.findById("63fbb9a06cdf695a8104adb2");
		Optional<Client> deletedClient2 = clientRepository.findById("63fc9a9d912111525e44aa31");
		assertFalse(deletedClient1.isPresent());
		assertFalse(deletedClient2.isPresent());

	}
	@Test
	public void testMiseEnVeille(){
		Client client=clientRepository.findById("63fb8ce9b114844bd801dfb4").get();
		client.setMiseEnVeille(true);
		clientRepository.save(client);

		Client updatedClient = clientRepository.findById("63fb8ce9b114844bd801dfb4").get();
		assertTrue(updatedClient.isMiseEnVeille());
	}
	@Test
	public void testAddContactClient(){
		Client client=clientRepository.findById("64006599df102013ae97d348").get();
		List<Contact> contacts=new ArrayList<>();
		Contact contact1=new Contact("achref","jjj","478596","achref@gmail.com","123045");
		//contact1 = ContactMapper.MAPPER.toContact(contactDto);

		contacts.add(contact1);
		contactRepository.save(contact1);
		client.setContact(contacts);
		clientRepository.save(client);
		Client updatedClient = clientRepository.findById("64006599df102013ae97d348").get();
		assertNotNull(updatedClient.getContact());
		assertEquals(1, updatedClient.getContact().size());
		assertEquals(contact1, updatedClient.getContact().get(0));


	}
	@Test
	public void testUpdateContactClient(){

		Client client=clientRepository.findClientByContactId("63fbb9c16cdf695a8104adb3").get();
		Contact contactToUpdate = contactRepository.findById("63fbb9c16cdf695a8104adb3").get();
		client.getContact().removeIf(contact1 -> contact1.equals(contactToUpdate));

		contactToUpdate.setNom("arij ");
		contactToUpdate.setEmail("arij.doe@example.com");
		contactToUpdate.setMobile("1235");
		contactToUpdate.setFonction("Director");
		contactToUpdate.setPhone("123456789");
		contactRepository.save(contactToUpdate);
		client.getContact().add(contactToUpdate);
		clientRepository.save(client);





	}
	@Test
	public void testDeleteContactClient(){
		Client client=clientRepository.findClientByContactId("63fbc31daac54631846057fb").get();
		Contact contact = contactRepository.findById("63fbc31daac54631846057fb").get();
		List<Contact> contactList = client.getContact();
		contactList.removeIf(c -> c.equals(contact));
		client.setContact(contactList);
		clientRepository.save(client);
		contactRepository.deleteById("63fbc31daac54631846057fb");
	}
	@Test
	public ResponseEntity<Map<String, Object>> testGetIdClients(){
		Client client=  clientRepository.findClientByRaisonSocial("Ma société").get();
		Map<String, Object> response = new HashMap<>();
		response.put("idClient", client.getId());
		response.put("refClient", client.getRefClientIris());
		return ResponseEntity.ok(response);

	}
	@Test
	public List<String> testGetRaisonSociales( ){
		List<Client> clients=new ArrayList<>();
		List<String> raisonSociale=new ArrayList<>();
		Client client1=clientRepository.findById("64006599df102013ae97d348").get();
		Client client2=clientRepository.findById("64011b5b867bcf7f664c927c").get();
		clients.add(client1);
		clients.add(client2);
		raisonSociale=clients.stream()
				.map(Client::getRaisonSocial)
				.collect(Collectors.toList());
		assertEquals(2, raisonSociale.size());
		assertTrue(raisonSociale.contains(client1.getRaisonSocial()));
		assertTrue(raisonSociale.contains(client2.getRaisonSocial()));
		return raisonSociale;



	}

	@Test
	public void testGetActiveClient(){
		List<ClientDto> activeClients=new ArrayList<>();
		Pageable paging= PageRequest.of(1, 10);
		Page<Client> pageTuts;
		pageTuts=clientRepository.findClientByMiseEnVeille(paging, false);
		activeClients = pageTuts.getContent().stream().map(client-> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeClients);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());
		assertTrue(pageTuts.getSize() <= paging.getPageSize());
		activeClients.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
	}
	@Test
	public void testGetNotActiveClient(){
		List<ClientDto> activeClients=new ArrayList<>();
		Pageable paging= PageRequest.of(1, 10);
		Page<Client> pageTuts;
		pageTuts=clientRepository.findClientByMiseEnVeille(paging, true);
		activeClients = pageTuts.getContent().stream().map(client-> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeClients);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());
		assertTrue(pageTuts.getSize() <= paging.getPageSize());
		activeClients.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
	}
	@Test
	public void testOnSortActiveClient(){
		List<ClientDto> activeClients=new ArrayList<>();

		Page<Client> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = clientRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC,"refClientIris" )));
		}
		else {
			pageTuts = clientRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "refClientIris")));
		}
		activeClients = pageTuts.getContent().stream().map(client-> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeClients);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		activeClients.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;

	}
	@Test
	public void  testOnSortNotActiveClient(){
		List<ClientDto> activeClients=new ArrayList<>();

		Page<Client> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = clientRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC,"refClientIris" )));
		}
		else {
			pageTuts = clientRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "refClientIris")));
		}
		activeClients = pageTuts.getContent().stream().map(client-> ClientMapper.MAPPER.toClientDto(client)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeClients);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		activeClients.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;


	}




	@Test
	public void testRemovePicture(){
		Picture picture=pictureRepository.findById("63fcc261e157b504b885c506").get();
		Client client = clientRepository.findClientByPictures(picture).get();
		pictureRepository.deleteById("63fcc261e157b504b885c506");
		client.getPictures().removeIf(picture1 -> picture1.equals(picture));
		clientRepository.save(client);
		// Vérification que  la photo a été supprimée
		assertFalse(client.getPictures().contains(picture));
		// Vérification que la liste de photos du client est vide
		assertFalse(pictureRepository.findById("63fcc261e157b504b885c506").isPresent());


	}
	@Test
	public void testRemovePictures(){
		Client client = clientRepository.findById("63fcc1e9e157b504b885c505").get();
		List<Picture> pictures = client.getPictures();
		for (Picture picture : pictures) {
			pictureRepository.deleteById(picture.getId());
		}
		client.getPictures().removeAll(client.getPictures());
		clientRepository.save(client);
		// Vérification que toutes les photos ont été supprimées
		for (Picture picture : pictures) {
			assertFalse(pictureRepository.existsById(picture.getId()));
		}
		// Vérification que la liste de photos du client est vide
		assertTrue(client.getPictures().isEmpty());

	}




}
