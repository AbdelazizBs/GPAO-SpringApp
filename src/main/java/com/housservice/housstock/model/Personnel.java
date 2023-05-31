package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;


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

	private int counter;

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

	private List<Picture> pictures;

	public Personnel(String nom, String prenom, String codePostal, String adresse, String photo, String matricule, String cin, String sexe, String ville, String rib, String poste, String phone, Date dateEmbauche, Date dateNaissance, String echelon, String numCnss, String situationFamiliale, String nbrEnfant, String typeContrat, String categorie, String fullName, String email, boolean miseEnVeille, List<Picture> pictures) {
		this.nom = nom;
		this.prenom = prenom;
		this.codePostal = codePostal;
		this.adresse = adresse;
		this.photo = photo;
		this.matricule = matricule;
		this.cin = cin;
		this.sexe = sexe;
		this.ville = ville;
		this.rib = rib;
		this.poste = poste;
		this.phone = phone;
		this.dateEmbauche = dateEmbauche;
		this.dateNaissance = dateNaissance;
		this.echelon = echelon;
		this.numCnss = numCnss;
		this.situationFamiliale = situationFamiliale;
		this.nbrEnfant = nbrEnfant;
		this.typeContrat = typeContrat;
		this.categorie = categorie;
		this.fullName = fullName;
		this.email = email;
		this.miseEnVeille = miseEnVeille;
		this.pictures = pictures;
	}

	public Personnel() {

	}


}
