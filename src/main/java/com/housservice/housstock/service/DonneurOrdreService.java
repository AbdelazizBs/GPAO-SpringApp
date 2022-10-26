package com.housservice.housstock.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.DonneurOrdre;
import com.housservice.housstock.model.dto.DonneurOrdreDto;


public interface DonneurOrdreService {
	
	public List<DonneurOrdre> findDonneurOrdreActif();
	public List<DonneurOrdre> findDonneurOrdreNonActive();
	public String getIdDonneurOrdres(String raisonSociale) throws ResourceNotFoundException;
	public List<String> getRaisonSociales();
	
	Optional<DonneurOrdre> getDonneurOrdreById(String id);
	
	public DonneurOrdreDto buildDonneurOrdreDtoFromDonneurOrdre(DonneurOrdre donneurOrdre);
	
	public void createNewDonneurOrdre(@Valid DonneurOrdreDto donneurOrdreDto);
	
	public void updateDonneurOrdre(@Valid DonneurOrdreDto donneurOrdreDto) throws ResourceNotFoundException;
	//public void addContactDonneurOrdre(@Valid ContactDonneurOrdre contactDonneurOrdre,String idDonneurOrdre ) throws ResourceNotFoundException;
	//public void updateContactDonneurOrdre(@Valid ContactDonneurOrdre contactDonneurOrdre, String idContact) throws ResourceNotFoundException;

	public void deleteDonneurOrdre(DonneurOrdre donneurOrdre);
	//public void deleteContactDonneurOrdre(String idContact) throws ResourceNotFoundException;
	

}
