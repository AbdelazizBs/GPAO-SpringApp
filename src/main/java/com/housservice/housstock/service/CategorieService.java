package com.housservice.housstock.service;

import java.util.List;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Categorie;
import com.housservice.housstock.model.dto.CategorieDto;


public interface CategorieService {
	
	public List<CategorieDto> getAllCategorie();
	
    public CategorieDto getCategorieById(String id);
	
    public CategorieDto buildCategorieDtoFromCategorie(Categorie categorie);

    public void createNewCategorie(@Valid CategorieDto categorieDto);
	
    public void updateCategorie(@Valid CategorieDto categorieDto) throws ResourceNotFoundException;
    
    public void deleteCategorie(String categorieId);
    
}
