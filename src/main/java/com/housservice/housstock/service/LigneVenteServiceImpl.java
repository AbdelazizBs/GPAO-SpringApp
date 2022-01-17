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
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.LigneVente;
import com.housservice.housstock.model.Ventes;
import com.housservice.housstock.model.dto.LigneVenteDto;
import com.housservice.housstock.repository.LigneVenteRepository;
import com.housservice.housstock.repository.VentesRepository;
import com.housservice.housstock.repository.ClientRepository;


@Service
public class LigneVenteServiceImpl implements LigneVenteService {
	
	private LigneVenteRepository ligneVenteRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private VentesRepository ventesRepository;
	
	private ClientRepository clientRepository;
	
	@Autowired
	public LigneVenteServiceImpl (LigneVenteRepository ligneVenteRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,VentesRepository ventesRepository,ClientRepository clientRepository)
	{
		this.ligneVenteRepository = ligneVenteRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.ventesRepository =  ventesRepository;
		this.clientRepository = clientRepository;
	}
	
	
	@Override
	public LigneVenteDto buildLigneVenteDtoFromLigneVente(LigneVente ligneVente) {
		if (ligneVente == null)
		{
			return null;
		}
			
		LigneVenteDto ligneVenteDto = new LigneVenteDto();
		ligneVenteDto.setId(ligneVente.getId());
		ligneVenteDto.setQuantite(ligneVente.getQuantite());
		ligneVenteDto.setPrixUnitaire(ligneVente.getPrixUnitaire());
		
		ligneVenteDto.setIdVentes(ligneVente.getVente().getId());
		ligneVenteDto.setCodeVentes(ligneVente.getVente().getCode());
		ligneVenteDto.setIdClient(ligneVente.getClient().getId());
		ligneVenteDto.setRaisonSocialClient(ligneVente.getClient().getRaisonSocial());
		
		return ligneVenteDto;
		
	}

	
	private LigneVente buildLigneVenteFromLigneVenteDto(LigneVenteDto ligneVenteDto) {
		
		LigneVente ligneVente = new LigneVente();
		ligneVente.setId(""+sequenceGeneratorService.generateSequence(LigneVente.SEQUENCE_NAME));	
		ligneVente.setQuantite(ligneVenteDto.getQuantite());
		ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());

		Ventes vt = ventesRepository.findById(ligneVenteDto.getIdVentes()).get();
		ligneVente.setVente(vt);
		
		Client cl = clientRepository.findById(ligneVenteDto.getIdClient()).get();
		ligneVente.setClient(cl);
		
		return ligneVente;
		
	}
	
	
	@Override
	public List<LigneVenteDto> getAllLigneVente() {
		
		List<LigneVente> listLigneVente = ligneVenteRepository.findAll();
		
		return listLigneVente.stream()
				.map(ligneVente -> buildLigneVenteDtoFromLigneVente(ligneVente))
				.filter(ligneVente -> ligneVente != null)
				.collect(Collectors.toList());
	}

	@Override
	public LigneVenteDto getLigneVenteById(String id) {
		
	    Optional<LigneVente> ligneVenteOpt = ligneVenteRepository.findById(id);
		if(ligneVenteOpt.isPresent()) {
			return buildLigneVenteDtoFromLigneVente(ligneVenteOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewLigneVente(@Valid LigneVenteDto ligneVenteDto) {
	
		ligneVenteRepository.save(buildLigneVenteFromLigneVenteDto(ligneVenteDto));
		
	}

	@Override
	public void updateLigneVente(@Valid LigneVenteDto ligneVenteDto) throws ResourceNotFoundException {
		
		LigneVente ligneVente = ligneVenteRepository.findById(ligneVenteDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneVenteDto.getId())));
		
		ligneVente.setQuantite(ligneVenteDto.getQuantite());
		ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
		
		if(ligneVente.getVente() == null || !StringUtils.equals(ligneVenteDto.getIdVentes(), ligneVente.getVente().getId())) 
		{
			Ventes vt = ventesRepository.findById(ligneVenteDto.getIdVentes()).get();
			ligneVente.setVente(vt);
		}
		
		if(ligneVente.getClient() == null || !StringUtils.equals(ligneVenteDto.getIdClient(), ligneVente.getClient().getId())) 
		{
			Client cl = clientRepository.findById(ligneVenteDto.getIdClient()).get();
			ligneVente.setClient(cl);
		}
		
		
		ligneVenteRepository.save(ligneVente);
	}

	@Override
	public void deleteLigneVente(String ligneVenteId) {
		
		ligneVenteRepository.deleteById(ligneVenteId);

		
	}

}
