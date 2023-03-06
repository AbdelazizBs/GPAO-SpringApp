package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.LigneCommandClientMapper;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.LigneCommandeClientRepository;
import com.housservice.housstock.repository.NomenclatureRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LigneCommandeClientServiceImpl implements LigneCommandeClientService {
	
	private LigneCommandeClientRepository ligneCommandeClientRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ArticleRepository articleRepository;
	
	private CommandeClientRepository commandeClientRepository;
	private final NomenclatureRepository nomenclatureRepository;

	@Autowired
	public LigneCommandeClientServiceImpl(LigneCommandeClientRepository ligneCommandeClientRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
			ArticleRepository articleRepository, CommandeClientRepository commandeClientRepository,
										  NomenclatureRepository nomenclatureRepository) {
		
		this.ligneCommandeClientRepository = ligneCommandeClientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.articleRepository = articleRepository;
		this.commandeClientRepository = commandeClientRepository;
		this.nomenclatureRepository = nomenclatureRepository;
	}

	@Override
	public LigneCommandeClientDto buildLigneCommandeClientDtoFromLigneCommandeClient(LigneCommandeClient ligneCommandeClient) {
		if (ligneCommandeClient == null)
		{
			return null;
		}
			
		LigneCommandeClientDto ligneCommandeClientDto = new LigneCommandeClientDto();
		ligneCommandeClientDto.setId(ligneCommandeClient.getId());
		ligneCommandeClientDto.setQuantite(ligneCommandeClient.getQuantite());
		ligneCommandeClientDto.setIdNomenclature(ligneCommandeClient.getNomenclature().getId());
		ligneCommandeClientDto.setDelai(ligneCommandeClient.getDelai());

		return ligneCommandeClientDto;
	}
	
	

	@Override
	public List<LigneCommandeClientDto> getAllLigneCommandeClient() {
		List<LigneCommandeClient> listLigneCommandeClient = ligneCommandeClientRepository.findAll();
		return listLigneCommandeClient.stream()
				.map(ligneCommandeClient -> buildLigneCommandeClientDtoFromLigneCommandeClient(ligneCommandeClient))
				.filter(ligneCommandeClient -> ligneCommandeClient != null)
				.collect(Collectors.toList());
	}
	@Override
	public List<LigneCommandeClientDto> getAllLigneCommandeClientFermer() {
		List<LigneCommandeClient> listLigneCommandeClient = ligneCommandeClientRepository.findAll();

		return listLigneCommandeClient.stream()
//				.filter(ligneCommandeClient -> ligneCommandeClient != null && ligneCommandeClient.getCommandeClient().isClosed())
				.map(ligneCommandeClient -> buildLigneCommandeClientDtoFromLigneCommandeClient(ligneCommandeClient))
				.collect(Collectors.toList());
	}

	@Override
	public LigneCommandeClientDto getLigneCommandeClientById(String id) {
		
		   Optional<LigneCommandeClient> ligneCommandeClientOpt = ligneCommandeClientRepository.findById(id);
			if(ligneCommandeClientOpt.isPresent()) {
				return buildLigneCommandeClientDtoFromLigneCommandeClient(ligneCommandeClientOpt.get());
			}
			return null;
	}
	@Override
	public List<LigneCommandeClient> getLignCmdByIdCmd(final String idCmd) throws ResourceNotFoundException {
		final CommandeClient commande  = commandeClientRepository.findById(idCmd).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idCmd)));
		return ligneCommandeClientRepository.findLigneCommandeClientByCommandeClient(commande) ;
	}


	@Override
	public void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {
		Nomenclature nomenclature = nomenclatureRepository.findById(ligneCommandeClientDto.getIdNomenclature()).orElseThrow(
				() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdNomenclature())));
		LigneCommandeClient ligneCommandeClient = LigneCommandClientMapper.MAPPER.toLigneCommandClient(ligneCommandeClientDto);
		ligneCommandeClient.setNomenclature(nomenclature);
		ligneCommandeClientRepository.save(ligneCommandeClient);
		CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdCommandeClient())));
		List<LigneCommandeClient> ligneCommandeClients= new ArrayList<>();
		ligneCommandeClients.add(ligneCommandeClient);
		commandeClient.setHaveLc(true);
		commandeClient.setLigneCommandeClient(ligneCommandeClients);
		commandeClientRepository.save(commandeClient);
	}

	@Override
	public void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {
		
		LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(ligneCommandeClientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getId())));
		
		ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
		ligneCommandeClient.setDelai(ligneCommandeClientDto.getDelai());

		if(ligneCommandeClient.getNomenclature() == null || !StringUtils.equals(ligneCommandeClientDto.getIdNomenclature(), ligneCommandeClient.getNomenclature().getId()))
		{
			Nomenclature article = nomenclatureRepository.findById(ligneCommandeClientDto.getIdNomenclature()).get();
			ligneCommandeClient.setNomenclature(article);
		}
		
		if(ligneCommandeClient.getCommandeClient() == null || !StringUtils.equals(ligneCommandeClientDto.getIdCommandeClient(), ligneCommandeClient.getCommandeClient().getId())) 
		{
			CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient()).get();
			ligneCommandeClient.setCommandeClient(commandeClient);
		}
		
		ligneCommandeClientRepository.save(ligneCommandeClient);
		
	}

	@Override
	public void deleteLigneCommandeClient(String ligneCommandeClientId) throws ResourceNotFoundException {
		LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(ligneCommandeClientId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientId)));
		CommandeClient commandeClient = commandeClientRepository.findCommandeClientByLigneCommandeClient(ligneCommandeClient)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClient)));
		for (LigneCommandeClient ligneCommandeClients  : commandeClient.getLigneCommandeClient()) {
			if (ligneCommandeClients.equals(ligneCommandeClient)) commandeClient.getLigneCommandeClient().remove(ligneCommandeClient);
		}
		if (commandeClient.getLigneCommandeClient().isEmpty()) {
			commandeClient.setHaveLc(false);
			commandeClient.setLigneCommandeClient(new ArrayList<>());}
		commandeClientRepository.save(commandeClient);
		ligneCommandeClientRepository.deleteById(ligneCommandeClientId);
	}




}
