package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.enums.TypeNomEnClature;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NomenclatureDto {
	
	@Id
	private String id;
    

	private String nomNomenclature;

	private String description;

	private TypeNomEnClature type;
	
	private String nature;
	private List<Nomenclature> childrens;
	private List<String> parentsName;

	private String categorie;
	
	private List <Picture> pictures;
	
	private Date date ;
	
	private int miseEnVeille;
	
	private Date dateMiseEnVeille;
	
}
