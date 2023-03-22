package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.CommandMapper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.CommandeClientRepository;
import com.housservice.housstock.repository.LigneCommandeClientRepository;
import com.housservice.housstock.repository.PlanificationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;

    private SequenceGeneratorService sequenceGeneratorService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    private ClientRepository clientRepository;
    final
    LigneCommandeClientRepository ligneCommandeClientRepository;


    final
    PlanificationRepository planificationRepository;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, SequenceGeneratorService sequenceGeneratorService,
                                     MessageHttpErrorProperties messageHttpErrorProperties, ClientRepository clientRepository, LigneCommandeClientRepository ligneCommandeClientRepository, PlanificationRepository planificationRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.clientRepository = clientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.planificationRepository = planificationRepository;
    }


    @Override
    public CommandeClientDto buildCommandeClientDtoFromCommandeClient(CommandeClient commandeClient) {
        if (commandeClient == null) {
            return null;
        }

        CommandeClientDto commandeClientDto = new CommandeClientDto();
        commandeClientDto.setId(commandeClient.getId());
        commandeClientDto.setTypeCmd(commandeClient.getTypeCmd());
        commandeClientDto.setNumCmd(commandeClient.getNumCmd());
        commandeClientDto.setHaveLc(commandeClient.getHaveLc());
        commandeClientDto.setDateCmd(commandeClient.getDateCmd());
        commandeClientDto.setEtatProduction(commandeClient.getEtatProduction());
        commandeClientDto.setDateCreationCmd(commandeClient.getDateCreationCmd());
        commandeClientDto.setIdClient(commandeClient.getClient().getId());
        commandeClientDto.setRaisonSocialClient(commandeClient.getClient().getRaisonSocial());
        //TODO
        // Liste ligneCommandeClients

        return commandeClientDto;

    }

    private CommandeClient buildCommandeClientFromCommandeClientDto(CommandeClientDto commandeClientDto) {
        CommandeClient commandeClient = new CommandeClient();

        commandeClient.setId("" + sequenceGeneratorService.generateSequence(CommandeClient.SEQUENCE_NAME));
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setTypeCmd(commandeClientDto.getTypeCmd());
        commandeClient.setNumCmd(commandeClientDto.getNumCmd());
        commandeClient.setEtatProduction(commandeClientDto.getEtatProduction());
        commandeClient.setDateCmd(commandeClientDto.getDateCmd());
        commandeClient.setHaveLc(commandeClientDto.getHaveLc());
        commandeClient.setDateCreationCmd(commandeClientDto.getDateCreationCmd());
        Client cl = clientRepository.findById(commandeClientDto.getIdClient()).get();
        commandeClient.setClient(cl);

        //TODO
        // Liste ligneCommandeClients

        return commandeClient;
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllCommandeClientNonFermer(int page, int size) {
        try {
            List<CommandeClientDto> commands;
            Pageable paging = PageRequest.of(page, size);
            Page<CommandeClient> pageTuts;
            pageTuts = commandeClientRepository.findCommandeClientByClosed(false, paging);
            commands = pageTuts.getContent().stream().map(commandeClient -> {
                return CommandMapper.MAPPER.toCommandDto(commandeClient);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("commandes", commands);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCommandeClientFermer(int page, int size) {
        try {
            List<CommandeClientDto> commands = new ArrayList<CommandeClientDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<CommandeClient> pageTuts;
            pageTuts = commandeClientRepository.findCommandeClientByClosed(true, paging);
            commands = pageTuts.getContent().stream().map(c -> buildCommandeClientDtoFromCommandeClient(c)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("commandes", commands);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public CommandeClientDto getCommandeClientById(String id) {

        Optional<CommandeClient> commandeClientOpt = commandeClientRepository.findById(id);
        if (commandeClientOpt.isPresent()) {
            return buildCommandeClientDtoFromCommandeClient(commandeClientOpt.get());
        }
        return null;
    }


    @Override
    public void createNewCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException {
        commandeClientDto.setDateCreationCmd(new Date());
        commandeClientDto.setClosed(false);
        commandeClientDto.setHaveLc(false);
        commandeClientDto.setLigneCommandeClient(new ArrayList<>());
        CommandeClient commandeClient = CommandMapper.MAPPER.toCommand(commandeClientDto);
        Client cl = clientRepository.findById(commandeClientDto.getIdClient()).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeClientDto.getIdClient())));
        commandeClient.setClient(cl);
        commandeClientRepository.save(commandeClient);
    }


    @Override
    public void updateCommandeClient(@Valid CommandeClientDto commandeClientDto) throws ResourceNotFoundException {

        CommandeClient commandeClient = commandeClientRepository.findById(commandeClientDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeClientDto.getId())));
        commandeClient.setTypeCmd(commandeClientDto.getTypeCmd());
        commandeClient.setNumCmd(commandeClientDto.getNumCmd());
        commandeClient.setEtatProduction(commandeClientDto.getEtatProduction());
        commandeClient.setDateCmd(commandeClientDto.getDateCmd());
        commandeClient.setDateCreationCmd(commandeClientDto.getDateCreationCmd());


        if (commandeClient.getClient() == null || !StringUtils.equals(commandeClientDto.getIdClient(), commandeClient.getClient().getId())) {
            Client client = clientRepository.findById(commandeClientDto.getIdClient()).get();
            commandeClient.setClient(client);
        }

        commandeClientRepository.save(commandeClient);

    }

    @Override
    public void fermeCmd(@Valid String idCmd) throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientRepository.findById(idCmd)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idCmd)));
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findLigneCommandeClientByIdCommandeClient(commandeClient.getId());
        commandeClient.setClosed(true);
        commandeClient.setEtatProduction("En attente");
        ligneCommandeClientRepository.saveAll(ligneCommandeClients);
        for (LigneCommandeClient ligneCommandeClient : ligneCommandeClients) {
            for (int j = 0; j < ligneCommandeClient.getNomenclature().getEtapeProductions().size(); j++) {
                PlanificationOf planificationOf = new PlanificationOf();
                planificationOf.setNomEtape(ligneCommandeClient.getNomenclature().getEtapeProductions().get(j).getNomEtape());
                planificationOf.setLigneCommandeClient(ligneCommandeClient);
                planificationOf.setQuantiteInitiale(ligneCommandeClient.getQuantite());
                planificationRepository.save(planificationOf);
            }
        }
        if (commandeClient.getClient() == null || !StringUtils.equals(commandeClient.getClient().getId(), commandeClient.getClient().getId())) {
            assert commandeClient.getClient() != null;
            Client client = clientRepository.findById(commandeClient.getClient().getId()).
                    orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), commandeClient.getClient().getId())));
            commandeClient.setClient(client);
        }
        commandeClientRepository.save(commandeClient);
    }


    @Override
    public void deleteCommandeClient(String commandeClientId) {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findLigneCommandeClientByIdCommandeClient(commandeClientId);
        ligneCommandeClients.forEach(ligneCommandeClient -> {
            List<PlanificationOf> planificationOf = planificationRepository.findPlanificationOfByLigneCommandeClient(ligneCommandeClient);
            planificationRepository.deleteAll(planificationOf);
        });
        ligneCommandeClientRepository.deleteAll(ligneCommandeClients);
        commandeClientRepository.deleteById(commandeClientId);

    }


}
