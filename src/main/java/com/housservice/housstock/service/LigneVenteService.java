package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneVente;
import com.housservice.housstock.model.dto.LigneVenteDto;

public interface LigneVenteService {
	
	public List<LigneVenteDto> getAllLigneVente();
	
    public LigneVenteDto getLigneVenteById(String id);
	
    public LigneVenteDto buildLigneVenteDtoFromLigneVente(LigneVente ligneVente);

    public void createNewLigneVente(@Valid LigneVenteDto ligneVenteDto);
	
    public void updateLigneVente(@Valid LigneVenteDto ligneVenteDto) throws ResourceNotFoundException;
    
    public void deleteLigneVente(String ligneVenteId);
}
