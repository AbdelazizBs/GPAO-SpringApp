package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeFournisseur;
import com.housservice.housstock.model.dto.CommandeFournisseurDto;

public interface CommandeFournisseurService {
	
	public List<CommandeFournisseurDto> getAllCommandeFournisseur();
	
    public CommandeFournisseurDto getCommandeFournisseurById(String id);
	
    public CommandeFournisseurDto buildCommandeFournisseurDtoFromCommandeFournisseur(CommandeFournisseur commandeFournisseur);

    public void createNewCommandeFournisseur(@Valid CommandeFournisseurDto commandeFournisseurDto);
	
    public void updateCommandeFournisseur(@Valid CommandeFournisseurDto commandeFournisseurDto) throws ResourceNotFoundException;
    
    public void deleteCommandeFournisseur(String commandeFournisseurId);

}
