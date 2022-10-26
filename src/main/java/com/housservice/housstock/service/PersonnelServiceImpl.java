package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.housservice.housstock.mapper.CompteMapper;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.repository.RolesRepository;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;

@Service
public class PersonnelServiceImpl implements PersonnelService {
	
	private PersonnelRepository personnelRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private EntrepriseRepository entrepriseRepository;
//	private final EmailValidator emailValidator;



	final
	RolesRepository rolesRepository;
	private ComptesRepository comptesRepository;
	
	@Autowired
	public PersonnelServiceImpl(PersonnelRepository personnelRepository,
								SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
								EntrepriseRepository entrepriseRepository,  ComptesRepository comptesRepository, RolesRepository rolesRepository)
{
		this.personnelRepository = personnelRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
		this.comptesRepository = comptesRepository;
		this.rolesRepository = rolesRepository;
}

	@Override
	public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel) throws ResourceNotFoundException {
		if (personnel == null)
		{
			return null;
		}
			
		PersonnelDto personnelDto = new PersonnelDto();
		personnelDto.setId(personnel.getId());
		personnelDto.setNom(personnel.getNom());
		personnelDto.setPrenom(personnel.getPrenom());
		personnelDto.setDateDeNaissance(personnel.getDateDeNaissance());
		personnelDto.setAdresse(personnel.getAdresse());
		personnelDto.setPhoto(personnel.getPhoto());
		if (personnel.getCompte()!=null ){
			personnelDto.setCompte(personnel.getCompte());
		}
		personnelDto.setCin(personnel.getCin());
		personnelDto.setSexe(personnel.getSexe());
		personnelDto.setRib(personnel.getRib());
		personnelDto.setPoste(personnel.getPoste());
		personnelDto.setDateDeEmbauche(personnel.getDateDeEmbauche());
		personnelDto.setEchelon(personnel.getEchelon());
		personnelDto.setCategorie(personnel.getCategorie());

		//TODO Liste Roles
		
		return personnelDto;
		
	}

	@Override
	public void createNewPersonnel(String nom,
								   String prenom,
								   Date dateDeNaissance,
								   String adresse,
								   String photo,
									 String cin,
									 String sexe,
									 String rib,
									 String poste,
									 Date datedembauche,
								   int echelon,
									 String categorie
									 ) throws ResourceNotFoundException {

		PersonnelDto personnelDto = new PersonnelDto();
		personnelDto.setNom(nom);
		personnelDto.setAdresse(adresse);
		personnelDto.setPrenom(prenom);
		personnelDto.setPhoto(photo);
		personnelDto.setCin(cin);
		personnelDto.setSexe(sexe);
		personnelDto.setRib(rib);
		personnelDto.setPoste(poste);
		personnelDto.setDateDeEmbauche(datedembauche);
		personnelDto.setEchelon(1);
		personnelDto.setCategorie(categorie);
		personnelDto.setDateDeNaissance(dateDeNaissance);
		personnelDto.setCompte(new Comptes());
		personnelDto.setMiseEnVeille(false);
		personnelRepository.save(buildUtilisateurFromUtilisateurDto(personnelDto));
	}

	private Personnel buildUtilisateurFromUtilisateurDto(PersonnelDto personnelDto)   {
		Personnel personnel = new Personnel();
		personnel.setId(""+sequenceGeneratorService.generateSequence(Personnel.SEQUENCE_NAME));
		personnel.setId(personnelDto.getId());
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setDateDeNaissance(personnelDto.getDateDeNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setPhoto(personnelDto.getPhoto());
		if (personnelDto.getCompte()!=null ){
			personnel.setCompte(personnelDto.getCompte());
		}
		personnel.setCin(personnelDto.getCin());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateDeEmbauche(personnelDto.getDateDeEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
		//TODO Liste Roles
		
		return personnel;
	}


	@Override
	public List<PersonnelDto> getAllPersonnel() {
		
	List<Personnel> listPersonnel = personnelRepository.findPersonnelByMiseEnVeille(false);
		
		return listPersonnel.stream()
				.map(utilisateur -> {
					try {
						return buildPersonnelDtoFromPersonnel(utilisateur);
					} catch (ResourceNotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.filter(utilisateur -> utilisateur != null)
				.collect(Collectors.toList());
	}

	@Override
	public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException {
		 Optional<Personnel> utilisateurOpt = personnelRepository.findById(id);
			if(utilisateurOpt.isPresent()) {
				return buildPersonnelDtoFromPersonnel(utilisateurOpt.get());
			}
			return null;
	}
	@Override
	public Personnel getPersonnelByEmail(String email) {
		Comptes comptes = comptesRepository.findByEmail(email);
return personnelRepository.findByCompte(comptes);
	}
	@Override
	public Personnel getPersonnelByNom(String nom) {
return  personnelRepository.findByNom(nom);
	}






	@Override
	public void updatePersonnel(@Valid PersonnelDto personnelDto) throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(personnelDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setDateDeNaissance(personnelDto.getDateDeNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setPhoto(personnelDto.getPhoto());
		personnel.setCin(personnelDto.getCin());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateDeEmbauche(personnelDto.getDateDeEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
		personnel.setCompte(personnelDto.getCompte());
		  if(personnel.getCompte() == null ||!StringUtils.equals(personnelDto.getCompte().getId(), personnel.getCompte().getId()))
		  {
		      personnel.setCompte(personnel.getCompte());
		  }
		personnelRepository.save(personnel);
	}

	@Override
	public void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
		personnel.setMiseEnVeille(true);
		personnelRepository.save(personnel);
	}

	@Override
	public List<PersonnelDto> getAllPersonnelEnVeille() {
		return  personnelRepository.findPersonnelByMiseEnVeille(true).stream().map(personnel -> {
			try {
				return buildPersonnelDtoFromPersonnel(personnel);
			} catch (ResourceNotFoundException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}


	@Override
	public void deletePersonnel(String utilisateurId) {
		personnelRepository.deleteById(utilisateurId);
	}


}
