package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonnelServiceImpl implements PersonnelService {

	private PersonnelRepository personnelRepository;

	private SequenceGeneratorService sequenceGeneratorService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	private EntrepriseRepository entrepriseRepository;
//	private final EmailValidator emailValidator;

	final RolesRepository rolesRepository;
	private ComptesRepository comptesRepository;

	@Autowired
	public PersonnelServiceImpl(PersonnelRepository personnelRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
			EntrepriseRepository entrepriseRepository, ComptesRepository comptesRepository,
			RolesRepository rolesRepository) {
		this.personnelRepository = personnelRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
		this.comptesRepository = comptesRepository;
		this.rolesRepository = rolesRepository;
	}

	@Override
	public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel) throws ResourceNotFoundException {
		if (personnel == null) {
			return null;
		}

		PersonnelDto personnelDto = new PersonnelDto();
		personnelDto.setId(personnel.getId());
		personnelDto.setNom(personnel.getNom());
		personnelDto.setPrenom(personnel.getPrenom());
		personnelDto.setDateNaissance(personnel.getDateNaissance());
		personnelDto.setAdresse(personnel.getAdresse());
		personnelDto.setPhoto(personnel.getPhoto());
		if (personnel.getCompte() != null) {
			personnelDto.setCompte(personnel.getCompte());
		}
		personnelDto.setCin(personnel.getCin());
		personnelDto.setSexe(personnel.getSexe());
		personnelDto.setRib(personnel.getRib());
		personnelDto.setPoste(personnel.getPoste());
		personnelDto.setDateEmbauche(personnel.getDateEmbauche());
		personnelDto.setEchelon(personnel.getEchelon());
		personnelDto.setCategorie(personnel.getCategorie());
		personnelDto.setMatricule(personnel.getMatricule());
		personnelDto.setPhone(personnel.getPhone());
		personnelDto.setVille(personnel.getVille());
		personnelDto.setCodePostal(personnel.getCodePostal());
		personnelDto.setEmail(personnel.getEmail());

		// TODO Liste Roles

		return personnelDto;

	}

	@Override
	public void createNewPersonnel(String nom, String prenom, LocalDate dateNaissance, String adresse, String matricule,
			String photo, String cin, String sexe, String rib, String poste, LocalDate dateEmbauche, String phone,
			String categorie, String ville, String codePostal, String email) {
		List<Personnel> personnelExisteWithMatricule = personnelRepository.findPersonnelByMatricule(matricule);
		List<Personnel> personnelExisteWithCin = personnelRepository.findPersonnelByCin(cin);
		if (!personnelExisteWithCin.isEmpty() || !personnelExisteWithMatricule.isEmpty()) {
			throw new RuntimeException("cin or matricule exist");
		}
		PersonnelDto personnelDto = new PersonnelDto();
		personnelDto.setNom(nom);
		personnelDto.setAdresse(adresse);
		personnelDto.setPrenom(prenom);
		personnelDto.setPhoto(photo);
		personnelDto.setCin(cin);
		personnelDto.setSexe(sexe);
		personnelDto.setRib(rib);
		personnelDto.setPoste(poste);
		personnelDto.setEchelon(1);
		personnelDto.setCategorie(categorie);
		personnelDto.setMatricule(matricule);
		personnelDto.setPhone(phone);
		personnelDto.setDateNaissance(dateNaissance.plusDays(1));
		personnelDto.setCompte(new Comptes());
		personnelDto.setDateEmbauche(dateEmbauche.plusDays(1));
		personnelDto.setMiseEnVeille(false);
		personnelDto.setVille(ville);
		personnelDto.setCodePostal(codePostal);
		personnelDto.setEmail(email);
		personnelRepository.save(buildUtilisateurFromUtilisateurDto(personnelDto));
	}

	private Personnel buildUtilisateurFromUtilisateurDto(PersonnelDto personnelDto) {
		Personnel personnel = new Personnel();
		personnel.setId("" + sequenceGeneratorService.generateSequence(Personnel.SEQUENCE_NAME));
		personnel.setId(personnelDto.getId());
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setDateNaissance(personnelDto.getDateNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setPhoto(personnelDto.getPhoto());
		if (personnelDto.getCompte() != null) {
			personnel.setCompte(personnelDto.getCompte());
		}
		personnel.setCin(personnelDto.getCin());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateEmbauche(personnelDto.getDateEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
		personnel.setMatricule(personnelDto.getMatricule());
		personnel.setPhone(personnelDto.getPhone());
		personnel.setVille(personnelDto.getVille());
		personnel.setCodePostal(personnelDto.getCodePostal());
		personnel.setEmail(personnelDto.getEmail());
		// TODO Liste Roles

		return personnel;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size) {

		try {

			List<PersonnelDto> personnels = new ArrayList<PersonnelDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByMiseEnVeille(false, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				try {
					return buildPersonnelDtoFromPersonnel(personnel);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException {
		Optional<Personnel> utilisateurOpt = personnelRepository.findById(id);
		if (utilisateurOpt.isPresent()) {
			return buildPersonnelDtoFromPersonnel(utilisateurOpt.get());
		}
		return null;
	}

	@Override
	public Personnel getPersonnelByEmail(String email) throws ResourceNotFoundException {
		Comptes comptes = comptesRepository.findByEmail(email);
		return personnelRepository.findByCompte(comptes).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), comptes)));
	}

	@Override
	public Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException {
		return personnelRepository.findByNom(nom).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), nom)));
	}

	@Override
	public void updatePersonnel(String idPersonnel, String nom, String prenom, LocalDate dateNaissance, String adresse,
			String matricule, String photo, String cin, String sexe, String rib, String poste, LocalDate dateEmbauche,
			int echelon, String phone, String categorie, String ville, String codePostal, String email)
			throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(idPersonnel).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
		personnel.setNom(nom);
		personnel.setPrenom(prenom);
		personnel.setDateNaissance(dateNaissance.plusDays(1));
		personnel.setAdresse(adresse);
		personnel.setPhoto(photo);
		personnel.setCin(cin);
		personnel.setSexe(sexe);
		personnel.setRib(rib);
		personnel.setPoste(poste);
		personnel.setDateEmbauche(dateEmbauche.plusDays(1));
		personnel.setEchelon(echelon);
		personnel.setCategorie(categorie);
		personnel.setMatricule(matricule);
		personnel.setPhone(phone);
		personnel.setVille(ville);
		personnel.setCodePostal(codePostal);
		personnel.setEmail(email);
		personnelRepository.save(personnel);
	}

	@Override
	public void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(idPersonnel).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
		personnel.setMiseEnVeille(true);
		personnelRepository.save(personnel);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByMiseEnVeille(true, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				try {
					return buildPersonnelDtoFromPersonnel(personnel);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();

			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> find(String textToFind, int page, int size) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByTextToFind(textToFind, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				try {
					return buildPersonnelDtoFromPersonnel(personnel);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void deletePersonnel(String utilisateurId) {
		personnelRepository.deleteById(utilisateurId);
	}

}
