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
import com.housservice.housstock.model.CommandeFournisseur;
import com.housservice.housstock.model.LigneCommandeFournisseur;
import com.housservice.housstock.model.dto.LigneCommandeFournisseurDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CommandeFournisseurRepository;
import com.housservice.housstock.repository.LigneCommandeFournisseurRepository;

@Service
public class LigneCommandeFournisseurServiceImpl implements LigneCommandeFournisseurService {

	private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ArticleRepository articleRepository;
	
	private CommandeFournisseurRepository commandeFournisseurRepository;

	@Autowired
	public LigneCommandeFournisseurServiceImpl(LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
			ArticleRepository articleRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
		
		this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.articleRepository = articleRepository;
		this.commandeFournisseurRepository = commandeFournisseurRepository;
	}

	@Override
	public LigneCommandeFournisseurDto buildLigneCommandeFournisseurDtoFromLigneCommandeFournisseur(LigneCommandeFournisseur ligneCommandeFournisseur) {
		if (ligneCommandeFournisseur == null)
		{
			return null;
		}
			
		LigneCommandeFournisseurDto ligneCommandeFournisseurDto = new LigneCommandeFournisseurDto();
		ligneCommandeFournisseurDto.setId(ligneCommandeFournisseur.getId());
		ligneCommandeFournisseurDto.setQuantite(ligneCommandeFournisseur.getQuantite());
		ligneCommandeFournisseurDto.setPrixUnitaire(ligneCommandeFournisseur.getPrixUnitaire());
		ligneCommandeFournisseurDto.setIdArticle(ligneCommandeFournisseur.getArticle().getId());
		ligneCommandeFournisseurDto.setDesignationArticle(ligneCommandeFournisseur.getArticle().getDesignation());
		ligneCommandeFournisseurDto.setIdCommandeFournisseur(ligneCommandeFournisseur.getCommandeFournisseur().getId());
		ligneCommandeFournisseurDto.setCodeCmdFournisseur(ligneCommandeFournisseur.getCommandeFournisseur().getCode());
		
		return ligneCommandeFournisseurDto;
	}
	
	
	private LigneCommandeFournisseur buildLigneCommandeFournisseurFromLigneCommandeFournisseurDto(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
		
		LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();
		ligneCommandeFournisseur.setId(""+sequenceGeneratorService.generateSequence(LigneCommandeFournisseur.SEQUENCE_NAME));	
		ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
		ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
		Article art = articleRepository.findById(ligneCommandeFournisseurDto.getIdArticle()).get();
		ligneCommandeFournisseur.setArticle(art);
		CommandeFournisseur cmdFr = commandeFournisseurRepository.findById(ligneCommandeFournisseurDto.getIdCommandeFournisseur()).get();
		ligneCommandeFournisseur.setCommandeFournisseur(cmdFr);

		return ligneCommandeFournisseur;
		
	}
	
	@Override
	public List<LigneCommandeFournisseurDto> getAllLigneCommandeFournisseur() {
		List<LigneCommandeFournisseur> listLigneCommandeFournisseur = ligneCommandeFournisseurRepository.findAll();
		
		return listLigneCommandeFournisseur.stream()
				.map(ligneCommandeFournisseur -> buildLigneCommandeFournisseurDtoFromLigneCommandeFournisseur(ligneCommandeFournisseur))
				.filter(ligneCommandeFournisseur -> ligneCommandeFournisseur != null)
				.collect(Collectors.toList());
	}

	@Override
	public LigneCommandeFournisseurDto getLigneCommandeFournisseurById(String id) {
		
		   Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOpt = ligneCommandeFournisseurRepository.findById(id);
			if(ligneCommandeFournisseurOpt.isPresent()) {
				return buildLigneCommandeFournisseurDtoFromLigneCommandeFournisseur(ligneCommandeFournisseurOpt.get());
			}
			return null;
	}


	@Override
	public void createNewLigneCommandeFournisseur(@Valid LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
		
		ligneCommandeFournisseurRepository.save(buildLigneCommandeFournisseurFromLigneCommandeFournisseurDto(ligneCommandeFournisseurDto));
		
	}

	@Override
	public void updateLigneCommandeFournisseur(@Valid LigneCommandeFournisseurDto ligneCommandeFournisseurDto) throws ResourceNotFoundException {
		
		LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findById(ligneCommandeFournisseurDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeFournisseurDto.getId())));
		
		ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
		ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
	
		if(ligneCommandeFournisseur.getArticle() == null || !StringUtils.equals(ligneCommandeFournisseurDto.getIdArticle(), ligneCommandeFournisseur.getArticle().getId())) 
		{
			Article article = articleRepository.findById(ligneCommandeFournisseurDto.getIdArticle()).get();
			ligneCommandeFournisseur.setArticle(article);
		}
		
		if(ligneCommandeFournisseur.getCommandeFournisseur() == null || !StringUtils.equals(ligneCommandeFournisseurDto.getIdCommandeFournisseur(), ligneCommandeFournisseur.getCommandeFournisseur().getId())) 
		{
			CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(ligneCommandeFournisseurDto.getIdCommandeFournisseur()).get();
			ligneCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		}
		
		ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
		
	}

	@Override
	public void deleteLigneCommandeFournisseur(String ligneCommandeFournisseurId) {
		
		ligneCommandeFournisseurRepository.deleteById(ligneCommandeFournisseurId);
		
	}

	
	
}
