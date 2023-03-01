package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.FournisseurMapper;
import com.housservice.housstock.mapper.ContactMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.repository.FournisseurRepository;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class FournisseurServiceImpl implements FournisseurService {

	private final FournisseurRepository fournisseurRepository;

	final
	PictureRepository pictureRepository;


	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final  ContactRepository contactRepository ;


	@Autowired
	public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,
							 MessageHttpErrorProperties messageHttpErrorProperties, ContactRepository contactRepository,
							 PictureRepository pictureRepository) {
		this.fournisseurRepository = fournisseurRepository;
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
	public Optional<Fournisseur> getFournisseurById(String fournisseurId) {
		return fournisseurRepository.findById(fournisseurId);
	}



	@Override
	public void deleteFournisseur(Fournisseur fournisseur) {
		fournisseurRepository.delete(fournisseur);

	}

	@Override
	public void deleteFournisseurSelected(List<String> idFournisseursSelected){
		for (String id : idFournisseursSelected){
			fournisseurRepository.deleteById(id);
		}
	}

	@Override
	public void createNewFournisseur( String refFournisseurIris,
								 String intitulee,
								 String adresse,
								 String codePostal,
								 String ville,
								 String pays,
								 String region,
								 String phone,
								 String email,
								 String statut,
								 String linkedin,
								 String abrege,
								 String siteWeb,
								 String nomBanque,
								 String adresseBanque,
								 String codeDouane,
								 String rne,
								 String identifiantTva,
								 String telecopie,
								 String rib,
								 String swift,
								 MultipartFile[] images) {
		if (fournisseurRepository.existsFournisseurByRefFournisseurIris(refFournisseurIris)) {
			throw new IllegalArgumentException(	"Matricule existe deja !!");
		}
		if (fournisseurRepository.existsFournisseurByRaisonSocial(intitulee)) {
			throw new IllegalArgumentException(	"Raison sociale existe deja !!");
		}
		FournisseurDto fournisseurDto = new FournisseurDto();
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
		fournisseurDto.setMiseEnVeille(false);
		fournisseurDto.setRefFournisseurIris(refFournisseurIris);
		fournisseurDto.setRaisonSocial(intitulee);
		fournisseurDto.setAdresse(adresse);
		fournisseurDto.setCodePostal(codePostal);
		fournisseurDto.setVille(ville);
		fournisseurDto.setPays(pays);
		fournisseurDto.setRegion(region);
		fournisseurDto.setPhone(phone);
		fournisseurDto.setEmail(email);
		fournisseurDto.setStatut(statut);
		fournisseurDto.setLinkedin(linkedin);
		fournisseurDto.setAbrege(abrege);
		fournisseurDto.setSiteWeb(siteWeb);

		fournisseurDto.setNomBanque(nomBanque);
		fournisseurDto.setAdresseBanque(adresseBanque);
		fournisseurDto.setCodeDouane(codeDouane);
		fournisseurDto.setRne(rne);
		fournisseurDto.setIdentifiantTva(identifiantTva);
		fournisseurDto.setTelecopie(telecopie);
		fournisseurDto.setRib(rib);
		fournisseurDto.setSwift(swift);
		List<Contact> contacts = new ArrayList<>();
		if (fournisseurDto.getContact()==null){
			fournisseurDto.setContact(contacts);
		}
		Fournisseur fournisseur = FournisseurMapper.MAPPER.toFournisseur(fournisseurDto);
		fournisseurRepository.save(fournisseur);
	}


	@Override
	public void miseEnVeille(String idFournisseur) throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idFournisseur)));
		fournisseur.setMiseEnVeille(true);
		fournisseurRepository.save(fournisseur);
	}

	@Override
	public void updateFournisseur(String idFournisseur ,String refFournisseurIris,
							 String intitulee,
							 String adresse,
							 String codePostal,
							 String ville,
							 String pays,
							 String region,
							 String phone,
							 String email,
							 String statut,
							 String linkedin,
							 String abrege,
							 String siteWeb, String nomBanque,
							 String adresseBanque,
							 String codeDouane,
							 String rne,
							 String identifiantTva,
							 String telecopie,
							 String rib,
							 String swift,
							 MultipartFile[] images) throws ResourceNotFoundException {

		if (!Objects.equals(email, "") && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Email invalide !!");
		}
		if (refFournisseurIris.isEmpty() || intitulee.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty() || pays.isEmpty() || region.isEmpty()) {
			throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
		}
		Fournisseur fournisseur = getFournisseurById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idFournisseur)));
		if (!fournisseur.getRefFournisseurIris().equals(refFournisseurIris)) {
			throw new IllegalArgumentException("Error Id!!");
		}
		if (!fournisseur.getRaisonSocial().equals(intitulee)) {
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
		pictures.addAll(fournisseur.getPictures());
		fournisseur.setPictures(pictures);
		fournisseur.setRaisonSocial(intitulee);
		fournisseur.setStatut(statut);
		fournisseur.setDate(fournisseur.getDate());
		fournisseur.setDateMiseEnVeille(fournisseur.getDateMiseEnVeille());
		fournisseur.setAdresse(adresse);
		fournisseur.setSiteWeb(siteWeb);
		fournisseur.setNomBanque(nomBanque);
		fournisseur.setAdresseBanque(adresseBanque);
		fournisseur.setRib(rib);
		fournisseur.setSwift(swift);
		fournisseur.setLinkedin(linkedin);
		fournisseur.setAbrege(abrege);
		fournisseur.setRefFournisseurIris(refFournisseurIris);
		fournisseur.setTelecopie(telecopie);
		fournisseur.setPhone(phone);
		fournisseur.setStatut(statut);
		fournisseur.setIdentifiantTva(identifiantTva);
		fournisseur.setCodePostal(codePostal);
		fournisseur.setVille(ville);
		fournisseur.setPays(pays);
		fournisseur.setRne(rne);
		fournisseur.setCodeDouane(codeDouane);
		fournisseur.setRegion(region);
		fournisseurRepository.save(fournisseur);

	}


	@Override
	public void addContactFournisseur(@Valid ContactDto contactDto , String idFournisseur ) throws ResourceNotFoundException {
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
	public void updateContactFournisseur(ContactDto contactDto,String idContact) throws ResourceNotFoundException {
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
	public ResponseEntity<Map<String, Object>>  getIdFournisseurs(String intitulee) throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurRepository.findFournisseurByRaisonSocial(intitulee).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),intitulee)));
		Map<String, Object> response = new HashMap<>();
		response.put("idFournisseur", fournisseur.getId());
		response.put("refFournisseur", fournisseur.getRefFournisseurIris());
		return ResponseEntity.ok(response);
	}

	@Override
	public List<String> getRaisonSociales()   {
		List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
		return fournisseurs.stream()
				.map(Fournisseur::getRaisonSocial)
				.collect(Collectors.toList());
	}


	@Override
	public ResponseEntity<Map<String, Object>> getActiveFournisseur(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts =  fournisseurRepository.findFournisseurByMiseEnVeille(paging, false);
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
	public ResponseEntity<Map<String, Object>> onSortActiveFournisseur(int page, int size, String field, String order) {
		try {
			List<FournisseurDto> fournisseurDtos ;
			Page<Fournisseur> pageTuts;
			if (order.equals("1")){
				pageTuts = fournisseurRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
			}
			else {
				pageTuts = fournisseurRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
			}
			fournisseurDtos = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
			}).collect(Collectors.toList());
			fournisseurDtos =fournisseurDtos.stream().filter(fournisseur -> !fournisseur.isMiseEnVeille()).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurDtos);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Override
	public ResponseEntity<Map<String, Object>> onSortFournisseurNotActive(int page, int size, String field, String order) {
		try {
			List<FournisseurDto> fournisseurDtos ;
			Page<Fournisseur> pageTuts;
			if (order.equals("1")){
				pageTuts = fournisseurRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
			}
			else {
				pageTuts = fournisseurRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
			}
			fournisseurDtos = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
			}).collect(Collectors.toList());
			fournisseurDtos =fournisseurDtos.stream().filter(FournisseurDto::isMiseEnVeille).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurDtos);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}





	@Override
	public ResponseEntity<Map<String, Object>> getFournisseurNotActive(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts =  fournisseurRepository.findFournisseurByMiseEnVeille(paging, true);
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


	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

		try {

			List<FournisseurDto> fournisseurs;
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByTextToFind(textToFind, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
			}).collect(Collectors.toList());
			fournisseurs= fournisseurs.stream().filter(fournisseur -> fournisseur.isMiseEnVeille()==enVeille).collect(Collectors.toList());
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
	public ResponseEntity<byte[]> RecordReport(String refFournisseurIris) {
		try{
			List<Fournisseur> fournisseur= fournisseurRepository.findByrefFournisseurIris(refFournisseurIris);
			File file = ResourceUtils.getFile("classpath:Fournisseurs.jrxml");
			JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fournisseur);
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

}
