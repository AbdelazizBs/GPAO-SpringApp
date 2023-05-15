package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.LigneCommandClientMapper;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import com.housservice.housstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LigneCommandeClientServiceImpl implements LigneCommandeClientService {

    private LigneCommandeClientRepository ligneCommandeClientRepository;

    private SequenceGeneratorService sequenceGeneratorService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    private ArticleRepository articleRepository;

    private CommandeClientRepository commandeClientRepository;
    private final NomenclatureRepository nomenclatureRepository;
    private final PlanificationRepository planificationRepository;

    @Autowired
    public LigneCommandeClientServiceImpl(LigneCommandeClientRepository ligneCommandeClientRepository,
                                          SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties,
                                          ArticleRepository articleRepository, CommandeClientRepository commandeClientRepository,
                                          NomenclatureRepository nomenclatureRepository,
                                          PlanificationRepository planificationRepository) {

        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.articleRepository = articleRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.nomenclatureRepository = nomenclatureRepository;
        this.planificationRepository = planificationRepository;
    }

    @Override
    public LigneCommandeClientDto buildLigneCommandeClientDtoFromLigneCommandeClient(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null) {
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
                .map(this::buildLigneCommandeClientDtoFromLigneCommandeClient)
                .filter(ligneCommandeClientDto -> !ligneCommandeClientDto.isLanced())
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClient> getAllLigneCommandeClientFermer() {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAll()
                .stream().filter(ligneCommandeClientDto -> !ligneCommandeClientDto.isLanced())
                .collect(Collectors.toList());
        return ligneCommandeClients;
    }
    @Override
    public List<LigneCommandeClient> getAllLigneCommandeClientLanced() {
       return  ligneCommandeClientRepository.findLigneCommandeClientByLanced(true);
    }


    private static boolean isValidAll(List<PlanificationOf> planificationOfs) {
        boolean isValid = false;
            for (PlanificationOf planificationOf : planificationOfs) {
                isValid = planificationOf.getDateLancementReel() != null
                        && planificationOf.getHeureDebutReel() != null
                        && planificationOf.getHeureFinReel() != null
                        && planificationOf.getQuantiteInitiale() != 0;
            }
            return isValid;
        }


    @Override
    public LigneCommandeClientDto getLigneCommandeClientById(String id) {
        Optional<LigneCommandeClient> ligneCommandeClientOpt = ligneCommandeClientRepository.findById(id);
        return ligneCommandeClientOpt.map(this::buildLigneCommandeClientDtoFromLigneCommandeClient).orElse(null);
    }

    @Override
    public List<LigneCommandeClient> getLignCmdByIdCmd(final String idCmd) throws ResourceNotFoundException {
        final CommandeClient commande = commandeClientRepository.findById(idCmd).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idCmd)));
        return ligneCommandeClientRepository.findLigneCommandeClientByIdCommandeClient(commande.getId());
    }


    @Override
    public void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {
        Nomenclature nomenclature = nomenclatureRepository.findById(ligneCommandeClientDto.getIdNomenclature()).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdNomenclature())));
        LigneCommandeClient ligneCommandeClient = LigneCommandClientMapper.MAPPER.toLigneCommandClient(ligneCommandeClientDto);
        ligneCommandeClient.setNomenclature(nomenclature);
        ligneCommandeClient.setLanced(false);
        ligneCommandeClientRepository.save(ligneCommandeClient);
        CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdCommandeClient())));
        List<LigneCommandeClient> ligneCommandeClients = new ArrayList<>();
        ligneCommandeClients.add(ligneCommandeClient);
        ligneCommandeClients.addAll(commandeClient.getLigneCommandeClient());
        ligneCommandeClient.setIdCommandeClient(commandeClient.getId());
        commandeClient.setHaveLc(true);
        commandeClient.setLigneCommandeClient(ligneCommandeClients);
        commandeClientRepository.save(commandeClient);
    }

    @Override
    public void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(ligneCommandeClientDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getId())));
        CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClientDto.getIdCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdCommandeClient())));
        commandeClient.getLigneCommandeClient().removeIf(ligneCommandeClient1 -> ligneCommandeClient1.equals(ligneCommandeClient));
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setDelai(ligneCommandeClientDto.getDelai());
        ligneCommandeClient.setNumCmdClient(ligneCommandeClientDto.getNumCmdClient());
        ligneCommandeClient.setIdCommandeClient(commandeClient.getId());
        Nomenclature nomenclature = nomenclatureRepository.findById(ligneCommandeClientDto.getIdNomenclature()).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientDto.getIdNomenclature())));
        ligneCommandeClient.setNomenclature(nomenclature);
        commandeClient.getLigneCommandeClient().add(ligneCommandeClient);
        commandeClientRepository.save(commandeClient);
        ligneCommandeClientRepository.save(ligneCommandeClient);

    }

    @Override
    public void lanceLc(String idLc) throws ResourceNotFoundException {
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(idLc)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idLc)));
        List<PlanificationOf> planificationOfs = planificationRepository.findAll().stream()
                .filter(planificationOf -> planificationOf.getLigneCommandeClient().getId().equals(idLc)).collect(Collectors.toList());
        planificationOfs.forEach(
                planificationOf -> {
                    planificationOf.setDateLancementReel(new Date());
                    planificationOf.setHeureDebutReel(new Date());
                    planificationOf.setHeureFinReel(new Date());
                    planificationOf.setLanced(true);
                    planificationRepository.save(planificationOf);
                }
        );
        if (isValidAll(planificationOfs)) {
            ligneCommandeClient.setLanced(true);
            ligneCommandeClientRepository.save(ligneCommandeClient);
        }else {
            throw new ResourceNotFoundException(MessageFormat.format("La ligne de commande client {0} n'est pas encore planifiée",idLc));
        }
    }

    @Override
    public void deleteLigneCommandeClient(String ligneCommandeClientId) throws ResourceNotFoundException {
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(ligneCommandeClientId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClientId)));
        CommandeClient commandeClient = commandeClientRepository.findById(ligneCommandeClient.getIdCommandeClient())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ligneCommandeClient)));
        commandeClient.getLigneCommandeClient().removeIf(ligneCommandeClient1 -> ligneCommandeClient1.equals(ligneCommandeClient));
        if (commandeClient.getLigneCommandeClient().isEmpty()) {
            commandeClient.setHaveLc(false);
        }
        commandeClientRepository.save(commandeClient);
        ligneCommandeClientRepository.deleteById(ligneCommandeClientId);
    }


}
