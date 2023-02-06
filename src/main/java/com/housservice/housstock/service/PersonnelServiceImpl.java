package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.RolesRepository;
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

	private final PersonnelRepository personnelRepository;

	final
	ComptesRepository comptesRepository;

	private final MessageHttpErrorProperties messageHttpErrorProperties;


	final RolesRepository rolesRepository;

	public PersonnelServiceImpl(PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties,
								RolesRepository rolesRepository, ComptesRepository comptesRepository) {
		this.personnelRepository = personnelRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.rolesRepository = rolesRepository;
		this.comptesRepository = comptesRepository;
	}


	@Override
	public void  addPersonnel(PersonnelDto personnelDto)   {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(personnelDto.getEmail());
		if (!personnelDto.getEmail().equals("") && !matcher.matches()) {
			throw new IllegalArgumentException("Email incorrecte !!");
		}
		if (personnelRepository.existsPersonnelByCin(personnelDto.getCin())) {
			throw new IllegalArgumentException(	" cin " + personnelDto.getCin() +  "  existe deja !!");
		}
		if (personnelRepository.existsPersonnelByMatricule(personnelDto.getMatricule())){
			throw new IllegalArgumentException( "matricule" + personnelDto.getMatricule() + "  existe deja !!");
		}
		Personnel personnel = PersonnelMapper.MAPPER.toPersonnel(personnelDto);
		personnel.setMiseEnVeille(false);
		 PersonnelMapper.MAPPER.toPersonnelDto(personnelRepository.save(personnel));
	}


	@Override
	public void  updatePersonnel(PersonnelDto personnelDto,String idPersonnel) throws ResourceNotFoundException {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(personnelDto.getEmail());
		Personnel personnel = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
		if (!personnelDto.getEmail().equals("") && !matcher.matches()) {
			throw new IllegalArgumentException("Email incorrecte !!");
		}
//		if (personnelRepository.existsPersonnelByCin(personnelDto.getCin())) {
//			throw new IllegalArgumentException(	" cin " + personnelDto.getCin() +  "  existe deja !!");
//		}
//		if (personnelRepository.existsPersonnelByMatricule(personnelDto.getMatricule())){
//			throw new IllegalArgumentException( "matricule" + personnelDto.getMatricule() + "  existe deja !!");
//		}
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
		personnelRepository.save(personnel);
	}

	@Override
	public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException {
		Optional<Personnel> utilisateurOpt = personnelRepository.findById(id);
		if (utilisateurOpt.isPresent()) {
			return PersonnelMapper.MAPPER.toPersonnelDto(utilisateurOpt.get());
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
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
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
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
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
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size,boolean enVeille) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByTextToFind(textToFind, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
			}).collect(Collectors.toList());
			personnels = personnels.stream().filter(personnel -> personnel.isMiseEnVeille() == enVeille).collect(Collectors.toList());
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
