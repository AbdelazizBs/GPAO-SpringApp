package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Entreprise;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EntrepriseDto;
import com.housservice.housstock.model.dto.EtapeProductionDto;
import com.housservice.housstock.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements EntrepriseService {

	private EntrepriseRepository entrepriseRepository;

	private SequenceGeneratorService sequenceGeneratorService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private EtapeProductionService etapeProductionservice;

	@Autowired
	public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,EtapeProductionService etapeProductionservice) {
		this.entrepriseRepository = entrepriseRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.etapeProductionservice = etapeProductionservice ;
	
	}

	@Override
	public EntrepriseDto buildEntrepriseDtoFromEntreprise(Entreprise entreprise) {
		if (entreprise == null) {
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

		if (entreprise.getListIdEtapeProductions() != null) {

			List<EtapeProductionDto> listEtapeProductionDtoChild = entreprise.getListIdEtapeProductions().stream()
					.map(etapeProductionId -> buildEtapeProductionDtoFromEtapeProduction(etapeProductionservice.getEtapeProductionById(etapeProductionId)))
					.filter(etapeProd -> etapeProd != null)
					.collect(Collectors.toList());

			entrepriseDto.setListEtapeProduction(listEtapeProductionDtoChild);
		}

		return entrepriseDto;

	}

	private EtapeProductionDto buildEtapeProductionDtoFromEtapeProduction(Optional<EtapeProduction> etapeProductionOp) {
		
		if (etapeProductionOp.isPresent()) {
			
			return etapeProductionservice.buildEtapeProductionDtoFromEtapeProduction(etapeProductionOp.get());
			
		}
		return null;
	}

	private Entreprise buildEntrepriseFromEntrepriseDto(EntrepriseDto entrepriseDto) {
		
		  Entreprise entreprise = new Entreprise();
		  entreprise.setId(""+sequenceGeneratorService.generateSequence(Entreprise.
		  SEQUENCE_NAME)); entreprise.setRaisonSocial(entrepriseDto.getRaisonSocial());
		  entreprise.setDescription(entrepriseDto.getDescription());
		  entreprise.setAdresse(entrepriseDto.getAdresse());
		  entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
		  entreprise.setEmail(entrepriseDto.getEmail());
		  entreprise.setNumTel(entrepriseDto.getNumTel());
		  
		  if (entrepriseDto.getListEtapeProduction() != null)
		  {
			  Set<String> listIdEtapeProductions = new HashSet<>(entrepriseDto.getListEtapeProduction().stream()
						  .map(IdEtapeProductionDto -> etapeProductionservice.getIdEtapeProductionFromEtapeProductionDto(IdEtapeProductionDto)) 
						  .filter(IdEtapeProd -> IdEtapeProd!= null) 
						  .collect(Collectors.toList()));
			  	  
			  entreprise.setListIdEtapeProductions(listIdEtapeProductions);
		  }
		    
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
		if (entrepriseOpt.isPresent()) {
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
				.orElseThrow(() -> new ResourceNotFoundException(
						MessageFormat.format(messageHttpErrorProperties.getError0002(), entrepriseDto.getId())));

		entreprise.setRaisonSocial(entrepriseDto.getRaisonSocial());
		entreprise.setDescription(entrepriseDto.getDescription());
		entreprise.setAdresse(entrepriseDto.getAdresse());
		entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
		entreprise.setEmail(entrepriseDto.getEmail());
		entreprise.setNumTel(entrepriseDto.getNumTel());

		entrepriseRepository.save(entreprise);
	}

	@Override
	public void deleteEntreprise(String entrepriseId) {
		entrepriseRepository.deleteById(entrepriseId);
		
	}

}
