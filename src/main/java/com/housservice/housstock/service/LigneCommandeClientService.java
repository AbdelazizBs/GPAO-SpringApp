package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;

import javax.validation.Valid;
import java.util.List;

public interface LigneCommandeClientService {

    public List<LigneCommandeClientDto> getAllLigneCommandeClient();

    public List<LigneCommandeClient> getAllLigneCommandeClientFermer();

    public LigneCommandeClientDto getLigneCommandeClientById(String id);

    public List<LigneCommandeClient> getLignCmdByIdCmd(String idCmd) throws ResourceNotFoundException;

    public LigneCommandeClientDto buildLigneCommandeClientDtoFromLigneCommandeClient(LigneCommandeClient ligneCommandeClient);

    public void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException;

    public void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException;

    public void deleteLigneCommandeClient(String ligneCommandeClientId) throws ResourceNotFoundException;
}
