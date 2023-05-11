package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;

import javax.validation.Valid;
import java.util.List;

public interface LigneCommandeClientService {

     List<LigneCommandeClientDto> getAllLigneCommandeClient();

     List<LigneCommandeClient> getAllLigneCommandeClientFermer();
     List<LigneCommandeClient> getAllLigneCommandeClientLanced();

     LigneCommandeClientDto getLigneCommandeClientById(String id);

     List<LigneCommandeClient> getLignCmdByIdCmd(String idCmd) throws ResourceNotFoundException;

     LigneCommandeClientDto buildLigneCommandeClientDtoFromLigneCommandeClient(LigneCommandeClient ligneCommandeClient);

     void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException;

     void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException;
     void lanceLc(String idLc) throws ResourceNotFoundException;

     void deleteLigneCommandeClient(String ligneCommandeClientId) throws ResourceNotFoundException;
}
