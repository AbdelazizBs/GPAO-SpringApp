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
	private List<String> clientId;
	private List<String> fournisseurId;
	private String description;
	private TypeNomEnClature type;
	private String nature;
	private List<String> childrensId;
	private List<String> parentsName;
	private List<Nomenclature> childrens;


	private List<String> parentsId;

	private String categorie;
	
	private List <Picture> pictures;
	
	private Date date ;
	
	private boolean miseEnVeille;
	
	private Date dateMiseEnVeille;
	
}
