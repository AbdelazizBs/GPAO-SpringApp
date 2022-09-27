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
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Entreprise;
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;
import com.housservice.housstock.repository.UtilisateurRepository;

@Service
public class ComptesServiceImpl implements ComptesService {
	
	private ComptesRepository comptesRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private EntrepriseRepository entrepriseRepository;
	
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	public ComptesServiceImpl(ComptesRepository comptesRepository, SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties, EntrepriseRepository entrepriseRepository,
			UtilisateurRepository utilisateurRepository) 
	{
		this.comptesRepository = comptesRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
		this.utilisateurRepository = utilisateurRepository;
	}

	@Override
	public ComptesDto buildComptesDtoFromComptes(Comptes comptes) {
		if (comptes == null)
		{
			return null;
		}
			
		ComptesDto comptesDto = new ComptesDto();
		comptesDto.setId(comptes.getId());
		comptesDto.setEmail(comptes.getEmail());
		comptesDto.setPassword(comptes.getPassword());
		comptesDto.setIdEntreprise(comptes.getEntreprise().getId());
		comptesDto.setRaisonSocialEntreprise(comptes.getEntreprise().getRaisonSocial());
//		comptesDto.setIdUtilisateur(comptes.getUtilisateur().getId());
//		comptesDto.setNomUtilisateur(comptes.getUtilisateur().getNom());
		
		return comptesDto;
		
	}
	
	private Comptes buildComptesFromComptesDto(ComptesDto comptesDto)
	{
		Comptes comptes = new Comptes();
		
		comptes.setId(""+sequenceGeneratorService.generateSequence(Comptes.SEQUENCE_NAME));	
		comptes.setId(comptesDto.getId());		
		comptes.setEmail(comptesDto.getEmail());
		comptes.setPassword(comptesDto.getPassword());

		Entreprise etr =
		entrepriseRepository.findById(comptesDto.getIdEntreprise()).get();
//		comptes.setEntreprise(etr); Utilisateur ut =
//		utilisateurRepository.findById(comptesDto.getIdUtilisateur()).get();
//		comptes.setUtilisateur(ut);
		
		return comptes;
	}


	@Override
	public List<ComptesDto> getAllComptes() {
		
	List<Comptes> listComptes = comptesRepository.findAll();
		
		return listComptes.stream()
				.map(comptes -> buildComptesDtoFromComptes(comptes))
				.filter(comptes -> comptes != null)
				.collect(Collectors.toList());
	}

	@Override
	public ComptesDto getComptesById(String id) {
		
		 Optional<Comptes> comptesOpt = comptesRepository.findById(id);
			if(comptesOpt.isPresent()) {
				return buildComptesDtoFromComptes(comptesOpt.get());
			}
			return null;
	}


	@Override
	public void createNewComptes(@Valid ComptesDto comptesDto) {
		
		comptesRepository.save(buildComptesFromComptesDto(comptesDto));
		
	}


	@Override
	public void updateComptes(@Valid ComptesDto comptesDto) throws ResourceNotFoundException {
		
		Comptes comptes = comptesRepository.findById(comptesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), comptesDto.getId())));
		
		comptes.setEmail(comptesDto.getEmail());
		comptes.setPassword(comptesDto.getPassword());
	
		
		  if(comptes.getEntreprise() == null ||!StringUtils.equals(comptesDto.getIdEntreprise(),comptes.getEntreprise().getId()))
		  { 
			  Entreprise etr = entrepriseRepository.findById(comptesDto.getIdEntreprise()).get();
			  comptes.setEntreprise(etr); }
		  
//		  if(comptes.getUtilisateur() == null ||!StringUtils.equals(comptesDto.getIdUtilisateur(),comptes.getUtilisateur().getId()))
//		  {
//			  Utilisateur ut = utilisateurRepository.findById(comptesDto.getIdUtilisateur()).get();
//		      comptes.setUtilisateur(ut); }
		 
		comptesRepository.save(comptes);
		
	}


	@Override
	public void deleteComptes(String comptesId) {
		
		comptesRepository.deleteById(comptesId);
		
	}
}
