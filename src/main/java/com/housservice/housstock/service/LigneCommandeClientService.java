package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;

public interface LigneCommandeClientService {

	public List<LigneCommandeClientDto> getAllLigneCommandeClient();
	
    public LigneCommandeClientDto getLigneCommandeClientById(String id);
	
    public LigneCommandeClientDto buildLigneCommandeClientDtoFromLigneCommandeClient(LigneCommandeClient ligneCommandeClient);

    public void createNewLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto);
	
    public void updateLigneCommandeClient(@Valid LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException;
    
    public void deleteLigneCommandeClient(String ligneCommandeClientId);
}