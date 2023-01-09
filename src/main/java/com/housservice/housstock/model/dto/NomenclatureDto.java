package com.housservice.housstock.model.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.housservice.housstock.model.Picture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomenclatureDto {
	
	@Id
	private String id;
    

	private String nomNomenclature;

	private String description;
	
	private String type;
	
	private String nature;

	private String categorie;
	
	private List <Picture> pictures;
	
	private Date date ;
	
	private int miseEnVeille;
	
	private Date dateMiseEnVeille;
	
}
