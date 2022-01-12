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
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.CommandeFournisseur;
import com.housservice.housstock.model.dto.CommandeFournisseurDto;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.repository.CommandeFournisseurRepository;

@Service
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
	
private CommandeFournisseurRepository commandeFournisseurRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private FournisseurRepository fournisseurRepository;
	
	@Autowired
	public CommandeFournisseurServiceImpl (CommandeFournisseurRepository commandeFournisseurRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,FournisseurRepository fournisseurRepository)
	{
		this.commandeFournisseurRepository = commandeFournisseurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.fournisseurRepository = fournisseurRepository;
	}
	
	@Override
	public CommandeFournisseurDto buildCommandeFournisseurDtoFromCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		if (commandeFournisseur == null)
		{
			return null;
		}
			
		CommandeFournisseurDto commandeFournisseurDto = new CommandeFournisseurDto();
		commandeFournisseurDto.setId(commandeFournisseur.getId());
		commandeFournisseurDto.setCode(commandeFournisseur.getCode());
		commandeFournisseurDto.setDateCommande(commandeFournisseur.getDateCommande());
		commandeFournisseurDto.setIdFournisseur(commandeFournisseur.getFournisseur().getId());
		commandeFournisseurDto.setRaisonSocialFournisseur(commandeFournisseur.getFournisseur().getRaisonSocial());
		//TODO
		// Liste ligneCommandeFournisseurs
		
		return commandeFournisseurDto;
		
	}
	
	
	private CommandeFournisseur buildCommandeFournisseurFromCommandeFournisseurDto(CommandeFournisseurDto commandeFournisseurDto)
	{
		CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
		
		commandeFournisseur.setId(""+sequenceGeneratorService.generateSequence(CommandeFournisseur.SEQUENCE_NAME));	
		commandeFournisseur.setId(commandeFournisseurDto.getId());
		
		
		commandeFournisseur.setCode(commandeFournisseurDto.getCode());
		commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
		Fournisseur fr = fournisseurRepository.findById(commandeFournisseurDto.getIdFournisseur()).get();
		commandeFournisseur.setFournisseur(fr);
		
		//TODO
		// Liste ligneCommandeFournisseurs
				
		return commandeFournisseur;
	}

	@Override
	public List<CommandeFournisseurDto> getAllCommandeFournisseur() {
		
	List<CommandeFournisseur> listCommandeFournisseur = commandeFournisseurRepository.findAll();
		
		return listCommandeFournisseur.stream()
				.map(commandefournisseur -> buildCommandeFournisseurDtoFromCommandeFournisseur(commandefournisseur))
				.filter(commandefournisseur -> commandefournisseur != null)
				.collect(Collectors.toList());
	}

	@Override
	public CommandeFournisseurDto getCommandeFournisseurById(String id) {
		
		 Optional<CommandeFournisseur> commandeFournisseurOpt = commandeFournisseurRepository.findById(id);
			if(commandeFournisseurOpt.isPresent()) {
				return buildCommandeFournisseurDtoFromCommandeFournisseur(commandeFournisseurOpt.get());
			}
			return null;
	}

	@Override
	public void createNewCommandeFournisseur(@Valid CommandeFournisseurDto commandeFournisseurDto) {
		
		commandeFournisseurRepository.save(buildCommandeFournisseurFromCommandeFournisseurDto(commandeFournisseurDto));
		
	}

	@Override
	public void updateCommandeFournisseur(@Valid CommandeFournisseurDto commandeFournisseurDto) throws ResourceNotFoundException {
		
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(commandeFournisseurDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeFournisseurDto.getId())));
		
	
		commandeFournisseur.setCode(commandeFournisseurDto.getCode());
		commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());

		if(commandeFournisseur.getFournisseur() == null || !StringUtils.equals(commandeFournisseurDto.getIdFournisseur(), commandeFournisseur.getFournisseur().getId())) 
		{
			Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getIdFournisseur()).get();
			commandeFournisseur.setFournisseur(fournisseur);
		}
		
		commandeFournisseurRepository.save(commandeFournisseur);
		
	}

	@Override
	public void deleteCommandeFournisseur(String commandeFournisseurId) {
		
		commandeFournisseurRepository.deleteById(commandeFournisseurId);
		
	}

}
