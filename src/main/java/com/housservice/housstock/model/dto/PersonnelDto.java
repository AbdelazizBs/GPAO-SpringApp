package com.housservice.housstock.model.dto;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.housservice.housstock.model.Comptes;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

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


	private Date dateDeEmbauche;


	private Date dateNaissance;


	private int echelon;



	private String matricule;



	private String phone;

	private String categorie;

	private Comptes compte;

	private boolean miseEnVeille ;


}
