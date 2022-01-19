package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Entreprise;
import com.housservice.housstock.model.dto.UtilisateurDto;
import com.housservice.housstock.repository.UtilisateurRepository;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	
	private UtilisateurRepository utilisateurRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private EntrepriseRepository entrepriseRepository;
	
	private ComptesRepository comptesRepository;
	
	@Autowired
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
			EntrepriseRepository entrepriseRepository, ComptesRepository comptesRepository)
{
		this.utilisateurRepository = utilisateurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
		this.comptesRepository = comptesRepository;
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
		
		utilisateurDto.setIdEntreprise(utilisateur.getEntreprise().getId());
		utilisateurDto.setRaisonSocialEntreprise(utilisateur.getEntreprise().getRaisonSocial());
		utilisateurDto.setIdComptes(utilisateur.getCompte().getId());
		utilisateurDto.setRaisonSocialComptes(utilisateur.getCompte().getRaisonSocial());
		
		//TODO Liste Roles
		
		return utilisateurDto;
		
	}
	
	private Utilisateur buildUtilisateurFromUtilisateurDto(UtilisateurDto utilisateurDto)
	{
		Utilisateur utilisateur = new Utilisateur();
		
		utilisateur.setId(""+sequenceGeneratorService.generateSequence(Utilisateur.SEQUENCE_NAME));	
		utilisateur.setId(utilisateurDto.getId());		
		utilisateur.setNom(utilisateurDto.getNom());
		utilisateur.setPrenom(utilisateurDto.getPrenom());
		utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setPhoto(utilisateurDto.getPhoto());

		Entreprise etr = entrepriseRepository.findById(utilisateurDto.getIdEntreprise()).get();
		utilisateur.setEntreprise(etr); 
		Comptes cpt = comptesRepository.findById(utilisateurDto.getIdComptes()).get();
		utilisateur.setCompte(cpt);
		
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
	public void createNewUtilisateur(@Valid UtilisateurDto utilisateurDto) {
		
		utilisateurRepository.save(buildUtilisateurFromUtilisateurDto(utilisateurDto));
		
	}


	@Override
	public void updateUtilisateur(@Valid UtilisateurDto utilisateurDto) throws ResourceNotFoundException {
		
		Utilisateur utilisateur = utilisateurRepository.findById(utilisateurDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), utilisateurDto.getId())));
		
		utilisateur.setNom(utilisateurDto.getNom());
		utilisateur.setPrenom(utilisateurDto.getPrenom());
		utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setPhoto(utilisateurDto.getPhoto());
		
		  if(utilisateur.getEntreprise() == null ||!StringUtils.equals(utilisateurDto.getIdEntreprise(),utilisateur.getEntreprise().getId()))
		  { 
			  Entreprise etr = entrepriseRepository.findById(utilisateurDto.getIdEntreprise()).get();
			  utilisateur.setEntreprise(etr); 
		  }
		  
		  if(utilisateur.getCompte() == null ||!StringUtils.equals(utilisateurDto.getIdComptes(),utilisateur.getCompte().getId()))
		  {
			  Comptes cpt = comptesRepository.findById(utilisateurDto.getIdComptes()).get();
		      utilisateur.setCompte(cpt); 
		      
		  }
		 
		  
		utilisateurRepository.save(utilisateur);
		
	}


	@Override
	public void deleteUtilisateur(String utilisateurId) {
		
		utilisateurRepository.deleteById(utilisateurId);
		
	}

}
