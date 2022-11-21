package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


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
	private String codePostal;



	
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
	private String matricule;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String cin;

	@NotBlank
	@Indexed(unique = true)
	private String phone;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String sexe;
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String ville;




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
//	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateEmbauche;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
//	@JsonFormat( pattern = "yyyy-MM-dd")
	private LocalDate dateNaissance;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int echelon;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String categorie;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String email;
	
	private Comptes compte;
	private boolean miseEnVeille ;


	public Personnel() {

	}

	public Personnel(String nom, String prenom, LocalDate dateNaissance, String adresse, String photo,
					 String cin, String sexe, String rib, String poste, LocalDate dateDeEmbauche,
					 int echelon, String categorie,String matricule, String phone,
					 Comptes compte, boolean miseEnVeille,String ville,String codePostal,
					 String email) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.photo = photo;
		this.cin = cin;
		this.sexe = sexe;
		this.rib = rib;
		this.poste = poste;
		this.dateEmbauche = dateDeEmbauche;
		this.echelon = echelon;
		this.categorie = categorie;
		this.compte = compte;
		this.miseEnVeille = miseEnVeille;
		this.matricule = matricule;
		this.phone = phone;
		this.ville = ville;
		this.codePostal = codePostal;
		this.email = email;
	}
}
