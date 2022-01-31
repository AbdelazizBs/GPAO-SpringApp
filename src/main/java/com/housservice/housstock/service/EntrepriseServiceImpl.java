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
import com.housservice.housstock.model.dto.EntrepriseDto;
import com.housservice.housstock.repository.EntrepriseRepository;
import com.housservice.housstock.repository.ComptesRepository;

@Service
public class EntrepriseServiceImpl implements EntrepriseService{

	private EntrepriseRepository entrepriseRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ComptesRepository comptesRepository;
	
	@Autowired
	public EntrepriseServiceImpl (EntrepriseRepository entrepriseRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,ComptesRepository comptesRepository)
	{
		this.entrepriseRepository = entrepriseRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.comptesRepository = comptesRepository;
	}
	
	@Override
	public EntrepriseDto buildEntrepriseDtoFromEntreprise(Entreprise entreprise) {
		if (entreprise == null)
		{
			return null;
		}
			
		EntrepriseDto entrepriseDto = new EntrepriseDto();
		entrepriseDto.setId(entreprise.getId());
		entrepriseDto.setRaisonSocial(entreprise.getRaisonSocial());
		entrepriseDto.setDescription(entreprise.getDescription());
		entrepriseDto.setAdresse(entreprise.getAdresse());
		entrepriseDto.setCodeFiscal(entreprise.getCodeFiscal());
		entrepriseDto.setEmail(entreprise.getEmail());
		entrepriseDto.setNumTel(entreprise.getNumTel());
		entrepriseDto.setIdCompte(entreprise.getCompte().getId());
		entrepriseDto.setRaisonSocialCompte(entreprise.getCompte().getRaisonSocial());
		
		//TODO Liste utilisateurs
		
		return entrepriseDto;
		
	}

	
	private Entreprise buildEntrepriseFromEntrepriseDto(EntrepriseDto entrepriseDto) {
		
		Entreprise entreprise = new Entreprise();
		entreprise.setId(""+sequenceGeneratorService.generateSequence(Entreprise.SEQUENCE_NAME));
		entreprise.setRaisonSocial(entrepriseDto.getRaisonSocial());
		entreprise.setDescription(entrepriseDto.getDescription());
		entreprise.setAdresse(entrepriseDto.getAdresse());
		entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
		entreprise.setEmail(entrepriseDto.getEmail());
		entreprise.setNumTel(entrepriseDto.getNumTel());			
		Comptes cpt = comptesRepository.findById(entrepriseDto.getIdCompte()).get();
		entreprise.setCompte(cpt);
		
		return entreprise;
		
	}
	
	
	@Override
	public List<EntrepriseDto> getAllEntreprise() {
		
		List<Entreprise> listEntreprise = entrepriseRepository.findAll();
		
		return listEntreprise.stream()
				.map(entreprise -> buildEntrepriseDtoFromEntreprise(entreprise))
				.filter(entreprise -> entreprise != null)
				.collect(Collectors.toList());
	}

	@Override
	public EntrepriseDto getEntrepriseById(String id) {
		
	    Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
		if(entrepriseOpt.isPresent()) {
			return buildEntrepriseDtoFromEntreprise(entrepriseOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewEntreprise(@Valid EntrepriseDto entrepriseDto) {
	
		entrepriseRepository.save(buildEntrepriseFromEntrepriseDto(entrepriseDto));
		
	}

	@Override
	public void updateEntreprise(@Valid EntrepriseDto entrepriseDto) throws ResourceNotFoundException {
		
		Entreprise entreprise = entrepriseRepository.findById(entrepriseDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), entrepriseDto.getId())));
		
		entreprise.setRaisonSocial(entrepriseDto.getRaisonSocial());
		entreprise.setDescription(entrepriseDto.getDescription());
		entreprise.setAdresse(entrepriseDto.getAdresse());
		entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
		entreprise.setEmail(entrepriseDto.getEmail());
		entreprise.setNumTel(entrepriseDto.getNumTel());
		
		if(entreprise.getCompte() == null || !StringUtils.equals(entrepriseDto.getIdCompte(), entreprise.getCompte().getId())) 
		{
			Comptes cpt = comptesRepository.findById(entrepriseDto.getIdCompte()).get();
			entreprise.setCompte(cpt);
		}
		
		entrepriseRepository.save(entreprise);
	}

	@Override
	public void deleteEntreprise(String entrepriseId) {
		
		entrepriseRepository.deleteById(entrepriseId);

		
	}
}
