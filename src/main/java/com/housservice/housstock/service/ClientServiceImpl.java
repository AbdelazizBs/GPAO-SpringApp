package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ClientDto;
import com.housservice.housstock.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

	private ClientRepository clientRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, SequenceGeneratorService sequenceGeneratorService,
									MessageHttpErrorProperties messageHttpErrorProperties) {
		this.clientRepository = clientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
	
	
	@Override
	public ClientDto buildClientDtoFromClient(Client client) {
		if (client == null) {
			return null;
		}
		
		ClientDto clientDto = new ClientDto();
		clientDto.setId(client.getId());
		
		clientDto.setRaison_social(client.getRaison_social());		
		clientDto.setRegime(client.getRegime());
		clientDto.setAdresse_facturation(client.getAdresse_facturation());
		clientDto.setAdresse_liv(client.getAdresse_liv());
		clientDto.setIncoterm(client.getIncoterm());
		clientDto.setEcheance(client.getEcheance());
		clientDto.setMode_pai(client.getMode_pai());
		clientDto.setNom_banque(client.getNom_banque());
		clientDto.setAdresse_banque(client.getAdresse_banque());
		clientDto.setRIB(client.getRIB());
		clientDto.setSWIFT(client.getSWIFT());
		clientDto.setBrancheActivite(client.getBrancheActivite());
		clientDto.setSecteurActivite(client.getSecteurActivite());
		
		return clientDto;
	}

	
	@Override
	public Optional<Client> getClientById(String clientId) {
		return clientRepository.findById(clientId);
	}

	
	@Override
	public void deleteClient(Client client) {
		clientRepository.delete(client);
		
	}

	@Override
	public void createNewClient(@Valid ClientDto clientDto) {
		
		clientRepository.save(buildClientFromClientDto(clientDto));
	}
	
	
	private Client buildClientFromClientDto(ClientDto clientDto) {
		Client client = new Client();
		client.setId(""+sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME));
		client.setRaison_social(clientDto.getRaison_social());		
		client.setRegime(clientDto.getRegime());
		client.setAdresse_facturation(clientDto.getAdresse_facturation());
		client.setAdresse_liv(clientDto.getAdresse_liv());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setMode_pai(clientDto.getMode_pai());
		client.setNom_banque(clientDto.getNom_banque());
		client.setAdresse_banque(clientDto.getAdresse_banque());
		client.setRIB(clientDto.getRIB());
		client.setSWIFT(clientDto.getSWIFT());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());
		
		return client;
	
		}


	@Override
	public void updateClient(@Valid ClientDto clientDto) throws ResourceNotFoundException {
		
		Client client = getClientById(clientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(),  clientDto.getId())));
		
		client.setRaison_social(clientDto.getRaison_social());		
		client.setRegime(clientDto.getRegime());
		client.setAdresse_facturation(clientDto.getAdresse_facturation());
		client.setAdresse_liv(clientDto.getAdresse_liv());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setMode_pai(clientDto.getMode_pai());
		client.setNom_banque(clientDto.getNom_banque());
		client.setAdresse_banque(clientDto.getAdresse_banque());
		client.setRIB(clientDto.getRIB());
		client.setSWIFT(clientDto.getSWIFT());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());

		clientRepository.save(client);
		
	}


	@Override
	public List<Client> findClientActif() {
		return clientRepository.findClientActif();
	}


	@Override
	public List<Client> findClientNotActif() {
		 return clientRepository.findClientNotActif();
	}



}
