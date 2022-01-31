package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;

public interface MatiereService {
	
	public List<MatiereDto> getAllMatiere();
	
    public MatiereDto getMatiereById(String id);
	
    public MatiereDto buildMatiereDtoFromMatiere(Matiere matiere);

    public void createNewMatiere(@Valid MatiereDto matiereDto);
	
    public void updateMatiere(@Valid MatiereDto matiereDto) throws ResourceNotFoundException;
    
    public void deleteMatiere(String matiereId);

}
