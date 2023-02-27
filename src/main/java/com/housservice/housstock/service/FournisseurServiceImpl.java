package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ContactMapper;
import com.housservice.housstock.mapper.FournisseurMapper;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


@Service
public class FournisseurServiceImpl implements FournisseurService{
	
	private final FournisseurRepository fournisseurRepository;
	
	//private final ArticleRepository articleRepository ;
	
	final PictureRepository pictureRepository;

	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	final  ContactRepository contactRepository ;
	private final NomenclatureRepository nomenclatureRepository;


	@Autowired
	public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, SequenceGeneratorService sequenceGeneratorService,
							 MessageHttpErrorProperties messageHttpErrorProperties, ContactRepository contactRepository, ArticleRepository articleRepository, PictureRepository pictureRepository,
								  NomenclatureRepository nomenclatureRepository) {
		this.fournisseurRepository = fournisseurRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.contactRepository = contactRepository;
		//this.articleRepository = articleRepository;
		this.pictureRepository = pictureRepository;
		this.nomenclatureRepository = nomenclatureRepository;
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
	public ResponseEntity<Map<String, Object>> findFournisseurActif(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts =  fournisseurRepository.findFournisseurActif(paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurs);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> findFournisseurNonActive(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts =  fournisseurRepository.findFournisseurNotActif(paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> FournisseurMapper.MAPPER.toFournisseurDto(fournisseur)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurs);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getIdFournisseurs(String intitule) throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurRepository.findFournisseurByIntitule(intitule).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),intitule)));
		Map<String, Object> response = new HashMap<>();
		response.put("idFournisseur", fournisseur.getId());
		response.put("refFrsIris", fournisseur.getRefFrsIris());
		return ResponseEntity.ok(response);
	}

	@Override
	public List<String> getIntitules() {
		List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
		return fournisseurs.stream()
				.map(Fournisseur::getIntitule)
				.collect(Collectors.toList());
	}


	@Override
	public void affecteNomEnClatureToFournisseur(String idFournisseur,
												 List<String> selectedOptions) throws ResourceNotFoundException {
		Fournisseur  fournisseur = fournisseurRepository.findById(idFournisseur).orElseThrow(() ->
				new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idFournisseur)));
		List<Nomenclature> nomenclatures = nomenclatureRepository.findNomenclatureByFournisseurId(idFournisseur);
		nomenclatures.forEach(nomenclature -> {
			nomenclature.getFournisseurId().removeIf(id -> id.equals(idFournisseur));
			nomenclatureRepository.save(nomenclature);
		});
		selectedOptions.forEach(option -> {
			try {
				Nomenclature nomenclature = nomenclatureRepository.findNomenclatureByNomNomenclature(option).orElseThrow(() ->
						new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),option)));
				List<String> frsId = new ArrayList<>();
				frsId.addAll(nomenclature.getFournisseurId());
				frsId.add(fournisseur.getId());
				nomenclature.setFournisseurId(frsId);
				nomenclatureRepository.save(nomenclature);
			} catch (ResourceNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
	}
	@Override
	public Optional<Fournisseur> getFournisseurById(String id) {
		return fournisseurRepository.findById(id);
	}


	@Override
	public ResponseEntity<Map<String, Object>> getFournisseursNameById(String nomenclatureId) throws ResourceNotFoundException {
		List<String> intitules = new ArrayList<>();
		Nomenclature nomenclature = nomenclatureRepository.findById(nomenclatureId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomenclatureId)));
		for (String id : nomenclature.getFournisseurId()) {
			Fournisseur fournisseur = fournisseurRepository.findById(id).orElseThrow(() ->
					new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
			intitules.add(fournisseur.getIntitule());
		}
		Map<String, Object> response = new HashMap<>();
		response.put("intitule", intitules);
		return ResponseEntity.ok(response);
	}

	@Override
	public void createNewFournisseur(String refFrsIris, String intitule, String abrege, String statut, String adresse,
			String codePostal, String ville, String region, String pays, String telephone, String telecopie,
			String linkedin, String email, String siteWeb, String nomBanque, String adresseBanque, String rib,
			String swift, String codeDouane, String rne, String identifiantTva, MultipartFile[] images)
			 {
				  
					if (fournisseurRepository.existsFournisseurByRefFrsIris(refFrsIris) || fournisseurRepository.existsFournisseurByIntitule(intitule)) {
						throw new IllegalArgumentException(	"Reference fournisseur iris ou Intitule existe deja !!");
					}
						FournisseurDto fournisseurDto = new FournisseurDto();
				 List<Contact> contacts = new ArrayList<>();
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

				fournisseurDto.setPictures(pictures);
				fournisseurDto.setDate(new Date());
				fournisseurDto.setMiseEnVeille(0);
				
				
				fournisseurDto.setRefFrsIris(refFrsIris);
				fournisseurDto.setIntitule(intitule);
				fournisseurDto.setAbrege(abrege);
				fournisseurDto.setStatut(statut);
				fournisseurDto.setAdresse(adresse);
				fournisseurDto.setCodePostal(codePostal);
				fournisseurDto.setVille(ville);
				fournisseurDto.setRegion(region);
				fournisseurDto.setPays(pays);
				fournisseurDto.setTelephone(telephone);
				fournisseurDto.setTelecopie(telecopie);
				fournisseurDto.setLinkedin(linkedin);
				fournisseurDto.setEmail(email);
				fournisseurDto.setSiteWeb(siteWeb);
				fournisseurDto.setNomBanque(nomBanque);
				fournisseurDto.setAdresseBanque(adresseBanque);
				fournisseurDto.setRib(rib);
				fournisseurDto.setSwift(swift);
				fournisseurDto.setCodeDouane(codeDouane);
				fournisseurDto.setRne(rne);
				fournisseurDto.setIdentifiantTva(identifiantTva);
				fournisseurDto.setContact(contacts);
					Fournisseur fournisseur = FournisseurMapper.MAPPER.toFournisseur(fournisseurDto);
					fournisseurRepository.save(fournisseur);
				}

	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {
		try {

			List<FournisseurDto> fournisseurs;
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurs);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void updateFournisseur(String idFournisseur, String refFrsIris, String intitule, String abrege,
			String statut, String adresse, String codePostal, String ville, String region, String pays,
			String telephone, String telecopie, String linkedin, String email, String siteWeb, String nomBanque,
			String adresseBanque, String rib, String swift, String codeDouane, String rne, String identifiantTva,
			MultipartFile[] images) throws ResourceNotFoundException {
		
		if (!Objects.equals(email, "") && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Email invalide !!");
		}
		
		if (refFrsIris.isEmpty() || intitule.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty() || pays.isEmpty() || region.isEmpty()) {
			throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
		}
		
		Fournisseur fournisseur = getFournisseurById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idFournisseur)));
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
		pictures.addAll(fournisseur.getPictures());
		fournisseur.setPictures(pictures);
		fournisseur.setDate(fournisseur.getDate());
		fournisseur.setMiseEnVeille(fournisseur.getMiseEnVeille());
		fournisseur.setDateMiseEnVeille(fournisseur.getDateMiseEnVeille());
		
		fournisseur.setRefFrsIris(refFrsIris);
		fournisseur.setIntitule(intitule);
		fournisseur.setAbrege(abrege);
		fournisseur.setStatut(statut);
		fournisseur.setAdresse(adresse);
		fournisseur.setCodePostal(codePostal);
		fournisseur.setVille(ville);
		fournisseur.setRegion(region);
		fournisseur.setPays(pays);
		fournisseur.setTelephone(telephone);
		fournisseur.setTelecopie(telecopie);
		fournisseur.setLinkedin(linkedin);
		fournisseur.setEmail(email);
		fournisseur.setSiteWeb(siteWeb);
		fournisseur.setNomBanque(nomBanque);
		fournisseur.setAdresseBanque(adresseBanque);
		fournisseur.setRib(rib);
		fournisseur.setSwift(swift);
		fournisseur.setCodeDouane(codeDouane);
		fournisseur.setRne(rne);
		fournisseur.setIdentifiantTva(identifiantTva);
		
		fournisseurRepository.save(fournisseur);
		
	}

	@Override
	public void miseEnVeille(String idFournisseur) throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idFournisseur)));
		fournisseur.setMiseEnVeille(1);
		fournisseurRepository.save(fournisseur);
		
	}

	@Override
	public void addContactFournisseur(ContactDto contactDto, String idFournisseur) throws ResourceNotFoundException {
		
		Fournisseur fournisseur = getFournisseurById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idFournisseur)));
		List<Contact> contacts = new ArrayList<>();
		Contact contact1 = ContactMapper.MAPPER.toContact(contactDto);
		if(fournisseur.getContact()==null){
			contacts.add(contact1);
			contactRepository.save(contact1);
			fournisseur.setContact(contacts);
			fournisseurRepository.save(fournisseur);
		}
		contactRepository.save(contact1);
		contacts.add(contact1);
		contacts.addAll(fournisseur.getContact());
		fournisseur.setContact(contacts);
		fournisseurRepository.save(fournisseur);
		
	}

	@Override
	public void updateContactFournisseur(ContactDto contactDto, String idContact) throws ResourceNotFoundException {
		
		Fournisseur fournisseur =fournisseurRepository.findFournisseurByContactId(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contactDto)));
		Contact contactToUpdate = contactRepository.findById(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contactDto.getId())));
		fournisseur.getContact().removeIf(contact1 -> contact1.equals(contactToUpdate));
		contactToUpdate.setNom(contactDto.getNom());
		contactToUpdate.setEmail(contactDto.getEmail());
		contactToUpdate.setMobile(contactDto.getMobile());
		contactToUpdate.setFonction(contactDto.getFonction());
		contactToUpdate.setPhone(contactDto.getPhone());
		contactRepository.save(contactToUpdate);
		fournisseur.getContact().add(contactToUpdate);
		fournisseurRepository.save(fournisseur);
		
	}

	@Override
	public void deleteFournisseur(Fournisseur fournisseur) {
		fournisseurRepository.delete(fournisseur);
		
	}

	@Override
	public void deleteFournisseurSelected(List<String> idFournisseursSelected) {
		for (String id : idFournisseursSelected){
			fournisseurRepository.deleteById(id);
		}
		
	}

	@Override
	public void deleteContactFournisseur(String idContact) throws ResourceNotFoundException {
		
		Fournisseur fournisseur = fournisseurRepository.findFournisseurByContactId(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
		Contact contact = contactRepository.findById(idContact)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
		List<Contact> contactList = fournisseur.getContact();
		contactList.removeIf(c -> c.equals(contact));
		fournisseur.setContact(contactList);
		fournisseurRepository.save(fournisseur);
		contactRepository.deleteById(idContact);
		
	}

	@Override
	public void removePictures(String idFournisseur) throws ResourceNotFoundException {
		
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idFournisseur)));
		for (String id : fournisseur.getPictures().stream().map(Picture::getId).collect(Collectors.toList())) {
			pictureRepository.deleteById(id);
		}
		fournisseur.getPictures().removeAll(fournisseur.getPictures());
		fournisseurRepository.save(fournisseur);
		
	}

	@Override
	public void removePicture(String idPic) throws ResourceNotFoundException {
		
		Picture picture = pictureRepository.findById(idPic)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPic)));
		Fournisseur fournisseur = fournisseurRepository.findFournisseurByPictures(picture)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), picture)));
		pictureRepository.deleteById(idPic);
		fournisseur.getPictures().removeIf(picture1 -> picture1.equals(picture));
		fournisseurRepository.save(fournisseur);
	}
	

	
}
