package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Comptes;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PersonnelDto {

	private String id;


	@NotEmpty
	@Size(min = 2, message = "personnel name should have at least 2 characters")
	private String nom;


	private String prenom;




	private String adresse;


	private String photo;
	private String ville;


	private String codePostal;


	private String cin;


	private String email;



	private String sexe;





	private String rib;


	private String poste;


	private Date dateEmbauche;

	private Date dateNaissance;

	private String echelon;
	private String numCnss;
	private String situationFamiliale;
	private String nbrEnfant;
	private String typeContrat;



	private String matricule;


	private String phone;

	private String categorie;

	private Comptes compte;

	private boolean miseEnVeille ;


}
