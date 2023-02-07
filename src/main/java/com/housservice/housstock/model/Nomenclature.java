package com.housservice.housstock.model;

import com.housservice.housstock.model.enums.TypeNomEnClature;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

	private TypeNomEnClature type;

	@NotBlank
	@Size(max = 100)
	private String nature;
	
	@NotBlank
	@Size(max = 100)
	private String categorie;

	private List<String> childrensId;
	private List<String> parentsName = new ArrayList<>();

	private List<String> parentsId;

	private List<Nomenclature> childrens;


	private List <Picture> pictures ;
	
	private Date date ;
	
	private int miseEnVeille;
	
	private Date dateMiseEnVeille;

	public Nomenclature() {
	
	}

	public Nomenclature(String id, @NotBlank @Size(max = 100) String nomNomenclature,
			@NotBlank @Size(max = 100) String description, TypeNomEnClature type,
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


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Nomenclature nomenclature = (Nomenclature) o;
		return id.equals(nomenclature.id);
	}
	
    
}
