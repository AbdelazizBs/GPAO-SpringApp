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
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.CommandeClientRepository;

@Service
public class CommandeClientServiceImpl implements CommandeClientService {

	private CommandeClientRepository commandeClientRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private ClientRepository clientRepository;
	
	@Autowired
	public CommandeClientServiceImpl (CommandeClientRepository commandeClientRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,ClientRepository clientRepository)
	{
		this.commandeClientRepository = commandeClientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.clientRepository = clientRepository;
	}
	
	
	@Override
	public CommandeClientDto buildCommandeClientDtoFromCommandeClient(CommandeClient commandeClient) {
		if (commandeClient == null)
		{
			return null;
		}
			
		CommandeClientDto commandeClientDto = new CommandeClientDto();
		commandeClientDto.setId(commandeClient.getId());
		commandeClientDto.setType_cmd(commandeClient.getType_cmd());
		commandeClientDto.setNum_cmd(commandeClient.getNum_cmd());
		commandeClientDto.setEtat(commandeClient.getEtat());
		commandeClientDto.setDate_cmd(commandeClient.getDate_cmd());
		commandeClientDto.setDate_creation_cmd(commandeClient.getDate_creation_cmd());
		commandeClientDto.setIdClient(commandeClient.getClient().getId());
		commandeClientDto.setRaisonSocialClient(commandeClient.getClient().getRaisonSocial());
		//TODO
		// Liste ligneCommandeClients
		
		return commandeClientDto;
		
	}
	
	private CommandeClient buildCommandeClientFromCommandeClientDto(CommandeClientDto commandeClientDto)
	{
		CommandeClient commandeClient = new CommandeClient();
		
		commandeClient.setId(""+sequenceGeneratorService.generateSequence(CommandeClient.SEQUENCE_NAME));	
		commandeClient.setId(commandeClientDto.getId());
		commandeClient.setType_cmd(commandeClientDto.getType_cmd());
		commandeClient.setNum_cmd(commandeClientDto.getNum_cmd());
		commandeClient.setEtat(commandeClientDto.getEtat());
		commandeClient.setDate_cmd(commandeClientDto.getDate_cmd());
		commandeClient.setDate_creation_cmd(commandeClientDto.getDate_creation_cmd());
		Client cl = clientRepository.findById(commandeClientDto.getIdClient()).get();
		commandeClient.setClient(cl);
		
		//TODO
		// Liste ligneCommandeClients
				
		return commandeClient;
	}


	@Override
	public List<CommandeClientDto> getAllCommandeClient() {
		
	List<CommandeClient> listCommandeClient = commandeClientRepository.findAll();
		
		return listCommandeClient.stream()
				.map(commandeclient -> buildCommandeClientDtoFromCommandeClient(commandeclient))
				.filter(commandeclient -> commandeclient != null)
				.collect(Collectors.toList());
	}


	@Override
	public CommandeClientDto getCommandeClientById(String id) {
		
		 Optional<CommandeClient> commandeClientOpt = commandeClientRepository.findById(id);
			if(commandeClientOpt.isPresent()) {
				return buildCommandeClientDtoFromCommandeClient(commandeClientOpt.get());
			}
			return null;
	}


	@Override
	public void createNewCommandeClient(@Valid CommandeClientDto commandeClientDto) {
		
		commandeClientRepository.save(buildCommandeClientFromCommandeClientDto(commandeClientDto));
		
	}


	@Override
	public void updateCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException {
		
		CommandeClient commandeClient = commandeClientRepository.findById(commandeClientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeClientDto.getId())));
		
		commandeClient.setType_cmd(commandeClientDto.getType_cmd());
		commandeClient.setNum_cmd(commandeClientDto.getNum_cmd());
		commandeClient.setEtat(commandeClientDto.getEtat());
		commandeClient.setDate_cmd(commandeClientDto.getDate_cmd());
		commandeClient.setDate_creation_cmd(commandeClientDto.getDate_creation_cmd());
	
		
		if(commandeClient.getClient() == null || !StringUtils.equals(commandeClientDto.getIdClient(), commandeClient.getClient().getId())) 
		{
			Client client = clientRepository.findById(commandeClientDto.getIdClient()).get();
			commandeClient.setClient(client);
		}
		
		commandeClientRepository.save(commandeClient);
		
	}


	@Override
	public void deleteCommandeClient(String commandeClientId) {
		
		commandeClientRepository.deleteById(commandeClientId);
		
	}
	
	
	
}
