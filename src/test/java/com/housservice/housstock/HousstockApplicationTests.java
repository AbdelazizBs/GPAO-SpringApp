package com.housservice.housstock;

import com.housservice.housstock.mapper.ClientMapper;
import com.housservice.housstock.mapper.FournisseurMapper;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.*;
import com.housservice.housstock.service.CommandeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private CommandeRepository commandeRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommandeService commandeService;
	@Autowired
	private MatierePrimaireRepository matierePrimaireRepository;

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
	@Test
	public void testCreateNewFournisseur(){
		Fournisseur fournisseur = new Fournisseur(new Date(),"ref100","Masociété","Régimeee","test","Linkdin","web","145827","001254","uib","marsa","145239987003325","SWIFT123","contact@masociete.com",false);

		fournisseurRepository.save(fournisseur);
	}
	@Test
	public void testGetFournisseurtById(){
		Fournisseur fournisseur=fournisseurRepository.findById("640f1783cb493c516924847d").get();
		System.out.println(fournisseur);
		//verification que  l'objet fournissseur n'est pas null
		assertNotNull(fournisseur);
		//verifier que le numero de telephone de premier contact de l'objet fournisseur est egale aver la valeur expecteé  "123456789".
		assertEquals("145827", fournisseur.getPhone());
	}
	@Test
	public void testUpdateFournisseur(){

		Fournisseur fournisseur=fournisseurRepository.findById("64013ae575f94978f6c052ed").get();
		if (fournisseur != null) {
			//client.setPhone("00123456");
			fournisseur.setRegion("tunis");
			fournisseurRepository.save(fournisseur);
		}

		System.out.println(fournisseur);
		Fournisseur updatedFournisseur = fournisseurRepository.findById("64013ae575f94978f6c052ed").orElse(null);
		assertNotNull(updatedFournisseur);

		assertEquals("tunis", updatedFournisseur.getAdresse());
	}
	@Test
	public void testDeleteFournisseur(){
		fournisseurRepository.deleteById("640064fddf102013ae97d347");
		//verifier que le fournisseur a ete supprime
		Optional<Fournisseur> deletedFournisseur = fournisseurRepository.findById("640064fddf102013ae97d347");
		assertFalse(deletedFournisseur.isPresent());
	}
	@Test
	public void testDeleteFournisseurSelected(){
		List<String> idFournisseursSelected=new ArrayList<>();
		idFournisseursSelected.add("63fbb9a06cdf695a8104adb2");
		idFournisseursSelected.add("63fc9a9d912111525e44aa31");
		for (String id : idFournisseursSelected){
			fournisseurRepository.deleteById(id);
		}
		Optional<Fournisseur> deletedFournisseur1 = fournisseurRepository.findById("63fbb9a06cdf695a8104adb2");
		Optional<Fournisseur> deletedFournisseur2 = fournisseurRepository.findById("63fc9a9d912111525e44aa31");
		assertFalse(deletedFournisseur1.isPresent());
		assertFalse(deletedFournisseur2.isPresent());

	}
	@Test
	public void testMiseEnVeilleFournisseur(){
		Fournisseur fournisseur=fournisseurRepository.findById("63fb8ce9b114844bd801dfb4").get();
		fournisseur.setMiseEnVeille(true);
		fournisseurRepository.save(fournisseur);

		Client updatedClient = clientRepository.findById("63fb8ce9b114844bd801dfb4").get();
		assertTrue(updatedClient.isMiseEnVeille());
	}
	@Test
	public void testAddContactFournisseur(){
		Fournisseur fournisseur=fournisseurRepository.findById("64006599df102013ae97d348").get();
		List<Contact> contacts=new ArrayList<>();
		Contact contact1=new Contact("achref","jjj","478596","achref@gmail.com","123045");
		//contact1 = ContactMapper.MAPPER.toContact(contactDto);

		contacts.add(contact1);
		contactRepository.save(contact1);
		fournisseur.setContact(contacts);
		fournisseurRepository.save(fournisseur);
		Fournisseur updatedFournisseur = fournisseurRepository.findById("64006599df102013ae97d348").get();
		assertNotNull(updatedFournisseur .getContact());
		assertEquals(1, updatedFournisseur .getContact().size());
		assertEquals(contact1, updatedFournisseur .getContact().get(0));


	}
	@Test
	public void testUpdateContactFournisseur(){

		Fournisseur fournisseur=fournisseurRepository.findFournisseurByContactId("63fbb9c16cdf695a8104adb3").get();
		Contact contactToUpdate = contactRepository.findById("63fbb9c16cdf695a8104adb3").get();
		fournisseur.getContact().removeIf(contact1 -> contact1.equals(contactToUpdate));

		contactToUpdate.setNom("arij ");
		contactToUpdate.setEmail("arij.doe@example.com");
		contactToUpdate.setMobile("1235");
		contactToUpdate.setFonction("Director");
		contactToUpdate.setPhone("123456789");
		contactRepository.save(contactToUpdate);
		fournisseur.getContact().add(contactToUpdate);
		fournisseurRepository.save(fournisseur);





	}
	@Test
	public void testDeleteContactFournisseur(){
		Fournisseur fournisseur=fournisseurRepository.findFournisseurByContactId("63fbc31daac54631846057fb").get();
		Contact contact = contactRepository.findById("63fbc31daac54631846057fb").get();
		List<Contact> contactList = fournisseur.getContact();
		contactList.removeIf(c -> c.equals(contact));
		fournisseur.setContact(contactList);
		fournisseurRepository.save(fournisseur);
		contactRepository.deleteById("63fbc31daac54631846057fb");
	}
	@Test
	public ResponseEntity<Map<String, Object>> testGetIdFournisseurs(){
		Fournisseur fournisseur=  fournisseurRepository.findFournisseurByRaisonSocial("Ma société").get();
		Map<String, Object> response = new HashMap<>();
		response.put("idClient", fournisseur.getId());
		response.put("refClient",fournisseur.getRefFournisseurIris());
		return ResponseEntity.ok(response);

	}
	@Test
	public List<String> testGetRaisonSocialesFournisseur( ){
		List<Fournisseur> fournisseurs=new ArrayList<>();
		List<String> raisonSociales=new ArrayList<>();
		Fournisseur fournisseur1=fournisseurRepository.findById("64006599df102013ae97d348").get();
		Fournisseur fournisseur2=fournisseurRepository.findById("64011b5b867bcf7f664c927c").get();
		fournisseurs.add(fournisseur1);
		fournisseurs.add(fournisseur2);
		raisonSociales=fournisseurs.stream()
				.map(Fournisseur::getRaisonSocial)
				.collect(Collectors.toList());
		assertEquals(2, raisonSociales.size());
		assertTrue(raisonSociales.contains(fournisseur1.getRaisonSocial()));
		assertTrue(raisonSociales.contains(fournisseur2.getRaisonSocial()));
		return raisonSociales;



	}

	@Test
	public void testGetActiveFournisseur(){
		List<FournisseurDto> activeFournisseurs=new ArrayList<>();
		Pageable paging= PageRequest.of(1, 10);
		Page<Fournisseur> pageTuts;
		pageTuts=fournisseurRepository.findFournisseurByMiseEnVeille(paging, false);
		activeFournisseurs = pageTuts.getContent().stream().map(fournisseur-> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("fournisseur", activeFournisseurs);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());
		assertTrue(pageTuts.getSize() <= paging.getPageSize());
		activeFournisseurs.forEach(fournisseurDto -> assertFalse(fournisseurDto.isMiseEnVeille()));
	}
	@Test
	public void testGetNotActiveFournisseur(){
		List<FournisseurDto> activeFournisseurs=new ArrayList<>();
		Pageable paging= PageRequest.of(1, 10);
		Page<Fournisseur> pageTuts;
		pageTuts=fournisseurRepository.findFournisseurByMiseEnVeille(paging, true);
		activeFournisseurs = pageTuts.getContent().stream().map(fournisseur-> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("fournisseur", activeFournisseurs);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());
		assertTrue(pageTuts.getSize() <= paging.getPageSize());
		activeFournisseurs.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
	}
	@Test
	public void testOnSortActiveFournisseeur(){
		List<FournisseurDto> activeFourniseurs=new ArrayList<>();

		Page<Fournisseur> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = fournisseurRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC,"refFournisseurIris" )));
		}
		else {
			pageTuts = fournisseurRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "refFournisseurIris")));
		}
		activeFourniseurs= pageTuts.getContent().stream().map(fournisseur-> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeFourniseurs);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		activeFourniseurs.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;

	}
	@Test
	public void  testOnSortNotActiveFournisseur(){
		List<FournisseurDto> activeFourniseurs=new ArrayList<>();

		Page<Fournisseur> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = fournisseurRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC,"refFournisseurIris" )));
		}
		else {
			pageTuts = fournisseurRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "refFournisseurIris")));
		}
		activeFourniseurs = pageTuts.getContent().stream().map(fournisseur-> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("client", activeFourniseurs);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		activeFourniseurs.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;


	}




	@Test
	public void testRemovePictureFournisseur(){
		Picture picture=pictureRepository.findById("63fcc261e157b504b885c506").get();
		Fournisseur fournisseur = fournisseurRepository.findFournisseurByPictures(picture).get();
		pictureRepository.deleteById("63fcc261e157b504b885c506");
		fournisseur.getPictures().removeIf(picture1 -> picture1.equals(picture));
		fournisseurRepository.save(fournisseur);
		// Vérification que  la photo a été supprimée
		assertFalse(fournisseur.getPictures().contains(picture));
		// Vérification que la liste de photos du fournisseur est vide
		assertFalse(pictureRepository.findById("63fcc261e157b504b885c506").isPresent());


	}
	@Test
	public void testRemovePicturesFournisseur(){
		Fournisseur fournisseur = fournisseurRepository.findById("63fcc1e9e157b504b885c505").get();
		List<Picture> pictures = fournisseur.getPictures();
		for (Picture picture : pictures) {
			pictureRepository.deleteById(picture.getId());
		}
		fournisseur.getPictures().removeAll(fournisseur.getPictures());
		fournisseurRepository.save(fournisseur);
		// Vérification que toutes les photos ont été supprimées
		for (Picture picture : pictures) {
			assertFalse(pictureRepository.existsById(picture.getId()));
		}
		// Vérification que la liste de photos du fournisseur est vide
		assertTrue(fournisseur.getPictures().isEmpty());

	}
	@Test
	public void testCreateNewCommande(){
		Commande commande=new Commande("2023/01/14","bnjn","14587","f2");
		commandeRepository.save(commande);

	}
	@Test
	public void testUpdateCommande(){
		Commande commande=commandeRepository.findById("64006599df102013ae97d348").get();
		if (commande != null) {

			commande.setNumBcd("010203");
			commandeRepository.save(commande);
		}

		System.out.println(commande);
		Commande updatedCommande = commandeRepository.findById("64006599df102013ae97d348").orElse(null);
		assertNotNull(updatedCommande);

		assertEquals("010203", updatedCommande.getNumBcd());

	}
	@Test
	public void testGetCommandeById(){
		Commande commande=commandeRepository.findById("63fbb9a06cdf695a8104adb2").get();
		System.out.println(commande);
		//verification que  l'objet ccommande n'est pas null
		assertNotNull(commande);
		//verifier que le numero bcd de l'objet commande  est egale aver la valeur expecteé  "123456789".
		assertEquals("123456789", commande.getNumBcd());
	}
	@Test
	public void testDeleteCommande(){
		commandeRepository.deleteById("63f73c2f5cfa3d46c96fe12b");
		//verifier que le commande a ete supprime
		Optional<Commande> deletedCommande = commandeRepository.findById("63f73c2f5cfa3d46c96fe12b");
		assertFalse(deletedCommande.isPresent());
	}
	@Test
	public void testDeleteCommandeSelected(){
		List<String> idCommandesSelected=new ArrayList<>();
		idCommandesSelected.add("63fbb9a06cdf695a8104adb2");
		idCommandesSelected.add("63fc9a9d912111525e44aa31");
		for (String id : idCommandesSelected){
			commandeRepository.deleteById(id);
		}
		Optional<Commande> deletedCommande1 = commandeRepository.findById("63fbb9a06cdf695a8104adb2");
		Optional<Commande> deletedCommande2 = commandeRepository.findById("63fc9a9d912111525e44aa31");
		assertFalse(deletedCommande1.isPresent());
		assertFalse(deletedCommande2.isPresent());

	}
	@Test
	public ResponseEntity<Map<String, Object>> testGetIdCommandes(){
		Commande commande=  commandeRepository.findCommandeByNumBcd("10254").get();
		Map<String, Object> response = new HashMap<>();
		response.put("idCommande", commande.getId());

		return ResponseEntity.ok(response);

	}
	@Test
	public void testAddArticleCommande(){
		Commande commande=commandeRepository.findById("64006599df102013ae97d348").get();
		Date date = new Date(2023, 3, 3);
		List<Article> articles=new ArrayList<>();
		Article article1=new Article("desi",date ,"bbbbb",100,145,1050);
		//contact1 = ContactMapper.MAPPER.toContact(contactDto);

		articles.add(article1);
		articleRepository.save(article1);
		commande.setArticle(articles);
		commandeRepository.save(commande);
		Commande updatedCommande = commandeRepository.findById("64006599df102013ae97d348").get();
		assertNotNull(updatedCommande.getArticle());
		assertEquals(1, updatedCommande.getArticle().size());
		assertEquals(article1, updatedCommande.getArticle().get(0));


	}
	@Test
	public void testUpdateArticleCommande(){
		Date date = new Date(2023, 3, 3);
		Commande commande=commandeRepository.findCommandeByArticleId("63fbb9c16cdf695a8104adb3").get();
		Article articleToUpdate = articleRepository.findById("63fbb9c16cdf695a8104adb3").get();
		commande.getArticle().removeIf(article1 -> article1.equals(articleToUpdate));

		articleToUpdate.setCommentaire("comment ");
		articleToUpdate.setQuantite(150);
		articleToUpdate.setPrix(1458);
		articleToUpdate.setDateLivraison(date);
		articleToUpdate.setPrixUnitaire(234567);
		articleRepository.save(articleToUpdate);
		commande.getArticle().add(articleToUpdate);
		commandeRepository.save(commande);





	}
	@Test
	public void testDeleteArticleCommande(){
		Commande commande=commandeRepository.findCommandeByArticleId("63fbc31daac54631846057fb").get();
		Article article = articleRepository.findById("63fbc31daac54631846057fb").get();
		List<Article> articleList = commande.getArticle();
		articleList.removeIf(c -> c.equals(article));
		commande.setArticle(articleList);
		commandeRepository.save(commande);
		articleRepository.deleteById("63fbc31daac54631846057fb");
	}
@Test
	public void testGetAllCommande(){
	/*Commande commande1=new Commande("2023/01/14","nothing","14478","f1");
	commandeRepository.save(commande1);
	Commande commande2=new Commande("2023/01/15","nothing","155478","f2");
	commandeRepository.save(commande2);*/

	List<Commande> commandes = commandeRepository.findAll();
	int nbCommande = commandes.size();
	assertEquals(12, nbCommande);

}
	@Test
	public void testGetAllClient(){


		List<Client> clients = clientRepository.findAll();
		int nbClient = clients.size();
		assertEquals(2, nbClient);

	}
	@Test
	public void testGetAllFournisseurs(){


		List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
		int nbFournisseur = fournisseurs.size();
		assertEquals(22, nbFournisseur);

	}
	@Test
	public void testAddMatiere(){
		MatierePrimaire matierePrimaire=new MatierePrimaire("carton");
		matierePrimaireRepository.save(matierePrimaire);
	}
	@Test
	public void testGetAllMatiere(){
		List<MatierePrimaire> matieres = matierePrimaireRepository.findAll();
		List<String> nbmatiere=new ArrayList<>();
		 nbmatiere=matieres.stream()
				.map(MatierePrimaire::getDesignation)
				.collect(Collectors.toList());
		assertEquals(1, matieres.size());

	}



}
