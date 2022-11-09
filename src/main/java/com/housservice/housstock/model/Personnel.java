package com.housservice.housstock.model;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "Personnel")
public class Personnel {

	@Transient
	public static final String SEQUENCE_NAME = "personnel_sequence";

	@Id
	private String id;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nom;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String prenom;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateDeNaissance;

	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String photo;

	@NotBlank
	@Size(min = 1,max = 9999)
	@Indexed(unique = true)
	private int matricule;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String cin;



	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String sexe;




	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String rib;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String poste;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateDeEmbauche;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int echelon;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String categorie;
	
	private Comptes compte;
	private boolean miseEnVeille ;


	public Personnel(String nom, String prenom, Date dateDeNaissance, String adresse, String photo, String cin, String sexe, String rib, String poste, Date dateDeEmbauche, int echelon, String categorie, int i, Comptes compte, boolean miseEnVeille) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateDeNaissance = dateDeNaissance;
		this.adresse = adresse;
		this.photo = photo;
		this.cin = cin;
		this.sexe = sexe;
		this.rib = rib;
		this.poste = poste;
		this.dateDeEmbauche = dateDeEmbauche;
		this.echelon = echelon;
		this.categorie = categorie;
		this.compte = compte;
		this.miseEnVeille = miseEnVeille;
	}

	public Personnel() {

	}
}
