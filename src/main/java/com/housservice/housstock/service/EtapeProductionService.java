package com.housservice.housstock.service;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EtapeProductionDto;


public interface EtapeProductionService {

	public List<EtapeProductionDto> getAllEtapeProduction();

    Optional<EtapeProduction> getEtapeProductionById(String id);
	
	public EtapeProductionDto buildEtapeProductionDtoFromEtapeProduction(EtapeProduction etapeProduction);

	public void createNewEtapeProduction(@Valid EtapeProductionDto etapeProductionDto);
	
	public void updateEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) throws ResourceNotFoundException;
	
	public void deleteEtapeProduction(EtapeProduction etapeProduction);

}
