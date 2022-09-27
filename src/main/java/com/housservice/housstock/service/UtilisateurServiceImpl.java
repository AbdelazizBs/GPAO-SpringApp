package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.housservice.housstock.model.Roles;
import com.housservice.housstock.repository.RolesRepository;
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
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.UtilisateurDto;
import com.housservice.housstock.repository.UtilisateurRepository;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
	
	private UtilisateurRepository utilisateurRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private EntrepriseRepository entrepriseRepository;

	private final PasswordEncoder passwordEncoder;

	final
	RolesRepository rolesRepository;
	private ComptesRepository comptesRepository;
	
	@Autowired
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
								  SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
								  EntrepriseRepository entrepriseRepository, PasswordEncoder passwordEncoder, ComptesRepository comptesRepository, RolesRepository rolesRepository)
{
		this.utilisateurRepository = utilisateurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
	this.passwordEncoder = passwordEncoder;
	this.comptesRepository = comptesRepository;
	this.rolesRepository = rolesRepository;
}

	@Override
	public UtilisateurDto buildUtilisateurDtoFromUtilisateur(Utilisateur utilisateur) {
		if (utilisateur == null)
		{
			return null;
		}
			
		UtilisateurDto utilisateurDto = new UtilisateurDto();
		utilisateurDto.setId(utilisateur.getId());
		utilisateurDto.setNom(utilisateur.getNom());
		utilisateurDto.setPrenom(utilisateur.getPrenom());
		utilisateurDto.setDateDeNaissance(utilisateur.getDateDeNaissance());
		utilisateurDto.setAdresse(utilisateur.getAdresse());
		utilisateurDto.setPhoto(utilisateur.getPhoto());
     	utilisateurDto.setRoles(utilisateur.getRoles());
//		utilisateurDto.setIdEntreprise(utilisateur.getEntreprise().getId());
//		utilisateurDto.setRaisonSocialEntreprise(utilisateur.getEntreprise().getRaisonSocial());
		utilisateurDto.setComptes(utilisateur.getCompte());

		//TODO Liste Roles
		
		return utilisateurDto;
		
	}

	@Override
	public void createNewUtilisateur(String nom, String prenom, Date dateDeNaissance, String adresse, String photo, String email, String password) throws ResourceNotFoundException {
		Comptes comptes = new Comptes();
		UtilisateurDto utilisateurDto = new UtilisateurDto();
		utilisateurDto.setNom(nom);
		utilisateurDto.setAdresse(adresse);
		utilisateurDto.setPrenom(prenom);
		utilisateurDto.setPhoto(photo);
		utilisateurDto.setDateDeNaissance(dateDeNaissance);
		comptes.setPassword(passwordEncoder.encode(password));
		comptes.setEmail(email);
		comptesRepository.save(comptes);
		utilisateurDto.setComptes(comptes);
		List<Roles> roles = new ArrayList<>();
		roles.add(rolesRepository.findByNom("ROLE_DEVELOPEMENT"));
		utilisateurDto.setRoles(roles);
		utilisateurRepository.save(buildUtilisateurFromUtilisateurDto(utilisateurDto));
	}

	private Utilisateur buildUtilisateurFromUtilisateurDto(UtilisateurDto utilisateurDto) throws ResourceNotFoundException {
		Utilisateur utilisateur = new Utilisateur();
		
		utilisateur.setId(""+sequenceGeneratorService.generateSequence(Utilisateur.SEQUENCE_NAME));	
		utilisateur.setId(utilisateurDto.getId());		
		utilisateur.setNom(utilisateurDto.getNom());
		utilisateur.setPrenom(utilisateurDto.getPrenom());
		utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setPhoto(utilisateurDto.getPhoto());
		Comptes comptes = comptesRepository.findById(utilisateurDto.getComptes().getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), utilisateurDto.getComptes().getId())));
		utilisateur.setCompte(comptes);
		List<Roles> roles = utilisateurDto.getRoles().stream().map(roles1 -> rolesRepository.findByNom(roles1.getNom())).collect(Collectors.toList());
		utilisateur.setRoles(roles);
		//TODO Liste Roles
		
		return utilisateur;
	}


	@Override
	public List<UtilisateurDto> getAllUtilisateur() {
		
	List<Utilisateur> listUtilisateur = utilisateurRepository.findAll();
		
		return listUtilisateur.stream()
				.map(utilisateur -> buildUtilisateurDtoFromUtilisateur(utilisateur))
				.filter(utilisateur -> utilisateur != null)
				.collect(Collectors.toList());
	}

	@Override
	public UtilisateurDto getUtilisateurById(String id) {
		
		 Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
			if(utilisateurOpt.isPresent()) {
				return buildUtilisateurDtoFromUtilisateur(utilisateurOpt.get());
			}
			return null;
	}
	@Override
	public Utilisateur getUtilisateurByEmail(String email) {
		Comptes comptes = comptesRepository.findByEmail(email);
		Utilisateur utilisateur = utilisateurRepository.findByCompte(comptes);
return utilisateur ;
	}
	@Override
	public Utilisateur getUtilisateurByNom(String nom) {
		Utilisateur utilisateur = utilisateurRepository.findByNom(nom);
return utilisateur ;
	}


//	@Override
//	public void createNewUtilisateur(final String nom,
//									 final  String prenom,
//									 final Date dateDeNaissance,
//									 final  String adresse,
//									 final  String photo,
//									 final  String raisonSocial,
//									 final  String password
//									 ) {
//		Comptes comptes = new Comptes();
//		UtilisateurDto utilisateurDto = new UtilisateurDto();
//		utilisateurDto.setNom(nom);
//		utilisateurDto.setAdresse(adresse);
//		utilisateurDto.setPrenom(prenom);
//		utilisateurDto.setPhoto(photo);
//		utilisateurDto.setDateDeNaissance(dateDeNaissance);
//		comptes.setPassword(password);
//		comptes.setRaisonSocial(raisonSocial);
//		utilisateurDto.setComptes(comptes);
//		List<Roles> roles = new ArrayList<>();
//		roles.add(rolesRepository.findByNom("ROLE_USER"));
//		utilisateurDto.setListRoles(roles);
//		utilisateurDto.setNom(nom);
//		utilisateurRepository.save(buildUtilisateurFromUtilisateurDto(utilisateurDto));
//
//	}


	@Override
	public void updateUtilisateur(@Valid UtilisateurDto utilisateurDto) throws ResourceNotFoundException {
		
		Utilisateur utilisateur = utilisateurRepository.findById(utilisateurDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), utilisateurDto.getId())));
		
		utilisateur.setNom(utilisateurDto.getNom());
		utilisateur.setPrenom(utilisateurDto.getPrenom());
		utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setPhoto(utilisateurDto.getPhoto());
//
//		  if(utilisateur.getEntreprise() == null ||!StringUtils.equals(utilisateurDto.getIdEntreprise(),utilisateur.getEntreprise().getId()))
//		  {
//			  Entreprise etr = entrepriseRepository.findById(utilisateurDto.getIdEntreprise()).get();
//			  utilisateur.setEntreprise(etr);
//		  }
		  
		  if(utilisateur.getCompte() == null ||!StringUtils.equals(utilisateurDto.getComptes().getId(),utilisateur.getCompte().getId()))
		  {
		      utilisateur.setCompte(utilisateur.getCompte());
		      
		  }
		 
		  
		utilisateurRepository.save(utilisateur);
		
	}


	@Override
	public void deleteUtilisateur(String utilisateurId) {
		
		utilisateurRepository.deleteById(utilisateurId);
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Comptes comptes = comptesRepository.findByEmail(email);
		Utilisateur utilisateur = utilisateurRepository.findByCompte(comptes);
		if (utilisateur == null){
			throw new UsernameNotFoundException("User not found in database");
		}else {
			System.out.println("user found in database");
		}
		Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
		utilisateur.getRoles().forEach(roles -> {authorities.add(new SimpleGrantedAuthority(roles.getNom()));});
		return new org.springframework.security.core.userdetails.User(utilisateur.getNom(),utilisateur.getCompte().getPassword(),authorities);
	}
}
