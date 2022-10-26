package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.DonneurOrdre;
import com.housservice.housstock.model.dto.DonneurOrdreDto;
import com.housservice.housstock.repository.DonneurOrdreRepository;

@Service
public class DonneurOrdreServiceImpl implements DonneurOrdreService{
	
    private DonneurOrdreRepository donneurOrdreRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	@Autowired
	public DonneurOrdreServiceImpl(DonneurOrdreRepository donneurOrdreRepository, SequenceGeneratorService sequenceGeneratorService,
							 MessageHttpErrorProperties messageHttpErrorProperties) {
		this.donneurOrdreRepository = donneurOrdreRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;

	}
	
	@Override
	public DonneurOrdreDto buildDonneurOrdreDtoFromDonneurOrdre(DonneurOrdre donneurOrdre) {
		if (donneurOrdre == null) {
			return null;
		}
		
		  DonneurOrdreDto donneurOrdreDto = new DonneurOrdreDto();
		  donneurOrdreDto.setId(donneurOrdre.getId());
		  
		  donneurOrdreDto.setRaisonSocial(donneurOrdre.getRaisonSocial());
		  donneurOrdreDto.setRegime(donneurOrdre.getRegime());
		  donneurOrdreDto.setAdresseFacturation(donneurOrdre.getAdresseFacturation());
		  donneurOrdreDto.setAdresseLivraison(donneurOrdre.getAdresseLivraison());
		  donneurOrdreDto.setIncoterm(donneurOrdre.getIncoterm());
		  donneurOrdreDto.setEcheance(donneurOrdre.getEcheance());
		  donneurOrdreDto.setMiseEnVeille(donneurOrdre.getMiseEnVeille());
		  donneurOrdreDto.setModePaiement(donneurOrdre.getModePaiement());
		  donneurOrdreDto.setNomBanque(donneurOrdre.getNomBanque());
		  donneurOrdreDto.setAdresseBanque(donneurOrdre.getAdresseBanque());
		  donneurOrdreDto.setRib(donneurOrdre.getRib()); 
		  donneurOrdreDto.setSwift(donneurOrdre.getSwift());
		  donneurOrdreDto.setBrancheActivite(donneurOrdre.getBrancheActivite());
		  donneurOrdreDto.setSecteurActivite(donneurOrdre.getSecteurActivite());
		 

		return donneurOrdreDto;
	}
	
	@Override
	public Optional<DonneurOrdre> getDonneurOrdreById(String id) {

		return donneurOrdreRepository.findById(id);
	}

	
	@Override
	public void deleteDonneurOrdre(DonneurOrdre donneurOrdre) {
		donneurOrdreRepository.delete(donneurOrdre);
		
	}
	
	@Override
	public void createNewDonneurOrdre(@Valid DonneurOrdreDto donneurOrdreDto) {
		donneurOrdreDto.setDate(LocalDate.now());
		donneurOrdreDto.setMiseEnVeille(0);
		donneurOrdreRepository.save(buildDonneurOrdreFromDonneurOrdreDto(donneurOrdreDto));
		
	}
	
	private DonneurOrdre buildDonneurOrdreFromDonneurOrdreDto(DonneurOrdreDto donneurOrdreDto) {
		DonneurOrdre donneurOrdre = new DonneurOrdre();
		donneurOrdre.setId(""+sequenceGeneratorService.generateSequence(DonneurOrdre.SEQUENCE_NAME));
		donneurOrdre.setRaisonSocial(donneurOrdreDto.getRaisonSocial());		
		donneurOrdre.setRegime(donneurOrdreDto.getRegime());
		donneurOrdre.setAdresseFacturation(donneurOrdreDto.getAdresseFacturation());
		donneurOrdre.setAdresseLivraison(donneurOrdreDto.getAdresseLivraison());
		donneurOrdre.setIncoterm(donneurOrdreDto.getIncoterm());
		donneurOrdre.setMiseEnVeille(donneurOrdreDto.getMiseEnVeille());
		donneurOrdre.setEcheance(donneurOrdreDto.getEcheance());
		donneurOrdre.setModePaiement(donneurOrdreDto.getModePaiement());
		donneurOrdre.setNomBanque(donneurOrdreDto.getNomBanque());
		donneurOrdre.setAdresseBanque(donneurOrdreDto.getAdresseBanque());
		donneurOrdre.setRib(donneurOrdreDto.getRib());
		donneurOrdre.setSwift(donneurOrdreDto.getSwift());
		donneurOrdre.setBrancheActivite(donneurOrdreDto.getBrancheActivite());
		donneurOrdre.setSecteurActivite(donneurOrdreDto.getSecteurActivite());
		
		return donneurOrdre;
	
		}
	
	@Override
	public void updateDonneurOrdre(@Valid DonneurOrdreDto donneurOrdreDto) throws ResourceNotFoundException {
		DonneurOrdre donneurOrdre = getDonneurOrdreById(donneurOrdreDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  donneurOrdreDto.getId())));
		donneurOrdre.setRaisonSocial(donneurOrdreDto.getRaisonSocial());		
		donneurOrdre.setRegime(donneurOrdreDto.getRegime());
		donneurOrdre.setMiseEnVeille(donneurOrdreDto.getMiseEnVeille());
		donneurOrdre.setDate(donneurOrdre.getDate());
		donneurOrdre.setDateMiseEnVeille(donneurOrdre.getDateMiseEnVeille());
		donneurOrdre.setAdresseFacturation(donneurOrdreDto.getAdresseFacturation());
		donneurOrdre.setAdresseLivraison(donneurOrdreDto.getAdresseLivraison());
		donneurOrdre.setIncoterm(donneurOrdreDto.getIncoterm());
		donneurOrdre.setEcheance(donneurOrdreDto.getEcheance());
		donneurOrdre.setModePaiement(donneurOrdreDto.getModePaiement());
		donneurOrdre.setNomBanque(donneurOrdreDto.getNomBanque());
		donneurOrdre.setAdresseBanque(donneurOrdreDto.getAdresseBanque());
		donneurOrdre.setRib(donneurOrdreDto.getRib());
		donneurOrdre.setSwift(donneurOrdreDto.getSwift());
		donneurOrdre.setBrancheActivite(donneurOrdreDto.getBrancheActivite());
		donneurOrdre.setSecteurActivite(donneurOrdreDto.getSecteurActivite());
		donneurOrdreRepository.save(donneurOrdre);
		
	}
	
	@Override
	public String getIdDonneurOrdres(String raisonSociale) throws ResourceNotFoundException {
		DonneurOrdre donneurOrdre = donneurOrdreRepository.findDonneurOrdreByRaisonSocial(raisonSociale).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),raisonSociale)));
		return donneurOrdre.getId() ;
	}
	
	@Override
	public List<String> getRaisonSociales() {
		List<DonneurOrdre> donneurOrdres = donneurOrdreRepository.findAll();
		return donneurOrdres.stream()
				.map(DonneurOrdre::getRaisonSocial)
				.collect(Collectors.toList());
	}

	
	@Override
	public List<DonneurOrdre> findDonneurOrdreActif() {
		return donneurOrdreRepository.findDonneurOrdreActif();
	}

	
	@Override
	public List<DonneurOrdre> findDonneurOrdreNonActive() {
		return donneurOrdreRepository.findDonneurOrdreNotActif();


	}
	
}
