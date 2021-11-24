package com.housservice.housstock.service;

import java.util.List;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EtapeProductionDto;
import java.util.Optional;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;

public interface EtapeProductionService {

	public List<EtapeProduction> getAllEtapeProduction();

    Optional<EtapeProduction> getEtapeProductionById(String id);
	
	EtapeProductionDto buildEtapeProductionDtoFromEtapeProduction(EtapeProduction etapeProduction);
	
	void deleteEtapeProduction(EtapeProduction etapeProduction);
	
	void createNewEtapeProduction(@Valid EtapeProductionDto etapeProductionDto);
	
	void updateEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) throws ResourceNotFoundException;

}
