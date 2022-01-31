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
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.LigneCommandeClientRepository;

@Service
public class LigneCommandeClientServiceImpl implements LigneCommandeClientService {
	
	private LigneCommandeClientRepository ligneCommandeClientRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ArticleRepository articleRepository;
	
	private CommandeClientRepository commandeClientRepository;
	
	@Autowired
	public LigneCommandeClientServiceImpl(LigneCommandeClientRepository ligneCommandeClientRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
			ArticleRepository articleRepository, CommandeClientRepository commandeClientRepository) {
		
		this.ligneCommandeClientRepository = ligneCommandeClientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.articleRepository = articleRepository;
		this.commandeClientRepository = commandeClientRepository;
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
		ligneCommandeClientDto.setPrixUnitaire(ligneCommandeClient.getPrixUnitaire());
		ligneCommandeClientDto.setIdArticle(ligneCommandeClient.getArticle().getId());
		ligneCommandeClientDto.setDesignationArticle(ligneCommandeClient.getArticle().getDesignation());
		ligneCommandeClientDto.setIdCommandeClient(ligneCommandeClient.getCommandeClient().getId());
		ligneCommandeClientDto.setNumCmdClient(ligneCommandeClient.getCommandeClient().getNumCmd());
		
		return ligneCommandeClientDto;
	}
	
	
	private LigneCommandeClient buildLigneCommandeClientFromLigneCommandeClientDto(LigneCommandeClientDto ligneCommandeClientDto) {
		
		LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
		ligneCommandeClient.setId(""+sequenceGeneratorService.generateSequence(LigneCommandeClient.SEQUENCE_NAME));	
		ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
		ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
		Article art = articleRepository.findById(ligneCommandeClientDto.getIdArticle()).get();
		ligneCommandeClient.setArticle(art);
		CommandeClient cmdCl = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient()).get();
		ligneCommandeClient.setCommandeClient(cmdCl);

		return ligneCommandeClient;
		
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
	public LigneCommandeClientDto getLigneCommandeClientById(String id) {
		
		   Optional<LigneCommandeClient> ligneCommandeClientOpt = ligneCommandeClientRepository.findById(id);
			if(ligneCommandeClientOpt.isPresent()) {
				return buildLigneCommandeClientDtoFromLigneCommandeClient(ligneCommandeClientOpt.get());
			}
			return null;
	}


	@Override
	public void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) {
		
		ligneCommandeClientRepository.save(buildLigneCommandeClientFromLigneCommandeClientDto(ligneCommandeClientDto));
		
	}

	@Override
	public void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {
		
		LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(ligneCommandeClientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getId())));
		
		ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
		ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
	
		if(ligneCommandeClient.getArticle() == null || !StringUtils.equals(ligneCommandeClientDto.getIdArticle(), ligneCommandeClient.getArticle().getId())) 
		{
			Article article = articleRepository.findById(ligneCommandeClientDto.getIdArticle()).get();
			ligneCommandeClient.setArticle(article);
		}
		
		if(ligneCommandeClient.getCommandeClient() == null || !StringUtils.equals(ligneCommandeClientDto.getIdCommandeClient(), ligneCommandeClient.getCommandeClient().getId())) 
		{
			CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient()).get();
			ligneCommandeClient.setCommandeClient(commandeClient);
		}
		
		ligneCommandeClientRepository.save(ligneCommandeClient);
		
	}

	@Override
	public void deleteLigneCommandeClient(String ligneCommandeClientId) {
		
		ligneCommandeClientRepository.deleteById(ligneCommandeClientId);
		
	}

}