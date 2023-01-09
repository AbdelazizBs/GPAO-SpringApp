package com.housservice.housstock.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "Nomenclature")
public class Nomenclature {
	
	@Transient
    public static final String SEQUENCE_NAME = "nomenclature_sequence";
	
	@Id
	private String id;
	
	// Référence IRIS
	
	// idParent

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nomNomenclature;
	
	@NotBlank
	@Size(max = 100)
	private String description;
	
	
	// type : Famille, Article,Element
	@NotBlank
	@Size(max = 100)
	private String type;
	
	
	// Simple, Composant
	@NotBlank
	@Size(max = 100)
	private String nature;
	
	@NotBlank
	@Size(max = 100)
	private String categorie;
	
	
	private List <Picture> pictures;
	
	private Date date ;
	
	private int miseEnVeille;
	
	private Date dateMiseEnVeille;

	public Nomenclature() {
	
	}

	public Nomenclature(String id, @NotBlank @Size(max = 100) String nomNomenclature,
			@NotBlank @Size(max = 100) String description, @NotBlank @Size(max = 100) String type,
			@NotBlank @Size(max = 100) String nature, @NotBlank @Size(max = 100) String categorie,
			Date date, int miseEnVeille, Date dateMiseEnVeille) {
		super();
		this.id = id;
		this.nomNomenclature = nomNomenclature;
		this.description = description;
		this.type = type;
		this.nature = nature;
		this.categorie = categorie;
		this.date = date;
		this.miseEnVeille = miseEnVeille;
		this.dateMiseEnVeille = dateMiseEnVeille;
	}
	
	
	
	
    
}
