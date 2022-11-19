package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Comptes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonnelDto {

	private String id;


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


	private LocalDate dateEmbauche;


	private LocalDate dateNaissance;


	private int echelon;



	private String matricule;



	private String phone;

	private String categorie;

	private Comptes compte;

	private boolean miseEnVeille ;


}
