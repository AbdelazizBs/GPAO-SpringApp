package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class PersonnelServiceImpl implements PersonnelService {

	private PersonnelRepository personnelRepository;

	private SequenceGeneratorService sequenceGeneratorService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	private EntrepriseRepository entrepriseRepository;

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
		personnelDto.setNumCnss(personnel.getNumCnss());
		personnelDto.setSituationFamiliale(personnel.getSituationFamiliale());
		personnelDto.setNbrEnfant(personnel.getNbrEnfant());
		personnelDto.setTypeContrat(personnel.getTypeContrat());

		return personnelDto;

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
		personnel.setMiseEnVeille(personnelDto.isMiseEnVeille());
		personnel.setNumCnss(personnelDto.getNumCnss());
		personnel.setSituationFamiliale(personnelDto.getSituationFamiliale());
		personnel.setNbrEnfant(personnelDto.getNbrEnfant());
		personnel.setTypeContrat(personnelDto.getTypeContrat());
		return personnel;
	}

	@Override
	public void  addPersonnel(PersonnelDto personnelDto)   {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(personnelDto.getEmail());
		if(!personnelDto.getEmail().equals("") && !matcher.matches()){
			throw new IllegalArgumentException("Email incorrecte !!");
		}
		if (personnelRepository.existsPersonnelByCin(personnelDto.getCin())|| personnelRepository.existsPersonnelByMatricule(personnelDto.getMatricule())) {
			throw new IllegalArgumentException(	"Personnel with cin " + personnelDto.getCin() + " or matricule " + personnelDto.getMatricule() + " already exists");
		}
		    personnelRepository.save(buildUtilisateurFromUtilisateurDto(personnelDto));
//		final Personnel personnel = PersonnelMapper.MAPPER.toPersonnel(personnelDto);
//		return PersonnelMapper.MAPPER.toPersonnelDto(personnelRepository.save(personnel));
	}


	@Override
	public void  updatePersonnel(PersonnelDto personnelDto) throws ResourceNotFoundException {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(personnelDto.getEmail());
		if(personnelDto.getNom().isEmpty() || personnelDto.getPrenom().isEmpty() || personnelDto.getAdresse().isEmpty()
				||  personnelDto.getCin().isEmpty()
				|| personnelDto.getDateEmbauche().toString().isEmpty() || personnelDto.getDateNaissance().toString().isEmpty())
		{
			throw new IllegalArgumentException("Voulez vous remplir le formulaire !");
		}
		if(!personnelDto.getEmail().equals("") && !matcher.matches()){
			throw new IllegalArgumentException( "Email incorrecte !!");
		}
		Personnel personnel = personnelRepository.findById(personnelDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setDateNaissance(personnelDto.getDateNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setPhoto(personnelDto.getPhoto());
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
		personnel.setNumCnss(personnelDto.getNumCnss());
		personnel.setSituationFamiliale(personnelDto.getSituationFamiliale());
		personnel.setNbrEnfant(personnelDto.getNbrEnfant());
		personnel.setTypeContrat(personnelDto.getTypeContrat());
		    personnelRepository.save(buildUtilisateurFromUtilisateurDto(personnelDto));

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
	public void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(idPersonnel).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
		personnel.setMiseEnVeille(true);
		personnelRepository.save(personnel);
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
					personnel.setDateEmbauche(personnel.getDateEmbauche());
					personnel.setDateNaissance(personnel.getDateNaissance());
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
	public ResponseEntity<Map<String, Object>> find(String textToFind, int page, int size,boolean enVeille) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
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
	public void deletePersonnel(String idPersonnel) {
		personnelRepository.deleteById(idPersonnel);
	}
	@Override
	public void deletePersonnelSelected(List<String> idPersonnelsSelected){
		for (String id : idPersonnelsSelected){
			personnelRepository.deleteById(id);
		}
	}

}
