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
		
		  ClientDto clientDto = new ClientDto(); clientDto.setId(client.getId());
		  
		  clientDto.setRaisonSocial(client.getRaisonSocial());
		  clientDto.setRegime(client.getRegime());
		  clientDto.setAdresseFacturation(client.getAdresseFacturation());
		  clientDto.setAdresseLivraison(client.getAdresseLivraison());
		  clientDto.setIncoterm(client.getIncoterm());
		  clientDto.setEcheance(client.getEcheance());
		  clientDto.setModePaiement(client.getModePaiement());
		  clientDto.setNomBanque(client.getNomBanque());
		  clientDto.setAdresseBanque(client.getAdresseBanque());
		  clientDto.setRib(client.getRib()); clientDto.setSwift(client.getSwift());
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
		client.setRaisonSocial(clientDto.getRaisonSocial());		
		client.setRegime(clientDto.getRegime());
		client.setAdresseFacturation(clientDto.getAdresseFacturation());
		client.setAdresseLivraison(clientDto.getAdresseLivraison());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setModePaiement(clientDto.getModePaiement());
		client.setNomBanque(clientDto.getNomBanque());
		client.setAdresseBanque(clientDto.getAdresseBanque());
		client.setRib(clientDto.getRib());
		client.setSwift(clientDto.getSwift());
		client.setBrancheActivite(clientDto.getBrancheActivite());
		client.setSecteurActivite(clientDto.getSecteurActivite());
		
		return client;
	
		}


	@Override
	public void updateClient(@Valid ClientDto clientDto) throws ResourceNotFoundException {
		
		Client client = getClientById(clientDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  clientDto.getId())));
		
		client.setRaisonSocial(clientDto.getRaisonSocial());		
		client.setRegime(clientDto.getRegime());
		client.setAdresseFacturation(clientDto.getAdresseFacturation());
		client.setAdresseLivraison(clientDto.getAdresseLivraison());
		client.setIncoterm(clientDto.getIncoterm());
		client.setEcheance(clientDto.getEcheance());
		client.setModePaiement(clientDto.getModePaiement());
		client.setNomBanque(clientDto.getNomBanque());
		client.setAdresseBanque(clientDto.getAdresseBanque());
		client.setRib(clientDto.getRib());
		client.setSwift(clientDto.getSwift());
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
