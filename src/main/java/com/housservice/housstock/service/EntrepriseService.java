package com.housservice.housstock.service;

import java.util.List;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Entreprise;
import com.housservice.housstock.model.dto.EntrepriseDto;

public interface EntrepriseService {
	
	public List<EntrepriseDto> getAllEntreprise();
	
    public EntrepriseDto getEntrepriseById(String id);
    
   // Optional<EtapeProduction> getEtapeProductionById(String id);
    
   // public String getIdEtapeProductionFromEtapeProductionDto(EtapeProductionDto etapeProductionDto);
	
    public EntrepriseDto buildEntrepriseDtoFromEntreprise(Entreprise entreprise);
	
    public void createNewEntreprise(@Valid EntrepriseDto entrepriseDto);
	
    public void updateEntreprise(@Valid EntrepriseDto entrepriseDto) throws ResourceNotFoundException;
    
    public void deleteEntreprise(String entrepriseId);
}
