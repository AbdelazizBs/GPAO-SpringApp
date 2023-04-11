package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@Document(collection = "Personnel")
public class Personnel {

	@Transient
	public static final String SEQUENCE_NAME = "personnel_sequence";

	@Id
	private String id;


	private String nom;


	private String prenom;

	private String codePostal;



	

	private String adresse;
	

	private String photo;


	private String matricule;


	private String cin;



	private String sexe;

	private String ville;


	private String rib;


	private String poste;


	private String phone;


	private Date dateEmbauche;



	private Date dateNaissance;



	private String echelon;

	private String numCnss;

	private String situationFamiliale;

	private String nbrEnfant;

	private String typeContrat;


	private String categorie;

	private String fullName;

	private String email;
	
	private boolean miseEnVeille ;



	public Personnel() {

	}

	public Personnel(String nom, String prenom, Date dateNaissance, String adresse, String photo,
					 String cin, String sexe, String rib, String poste, Date dateDeEmbauche,
					 String echelon, String categorie, String matricule, String phone,
					boolean miseEnVeille, String ville, String codePostal,
					 String email,String fullName) {
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
		this.miseEnVeille = miseEnVeille;
		this.matricule = matricule;
		this.phone = phone;
		this.ville = ville;
		this.codePostal = codePostal;
		this.email = email;
		this.fullName=fullName;
	}
}
