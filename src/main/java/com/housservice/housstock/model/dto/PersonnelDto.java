package com.housservice.housstock.model.dto;


import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.File;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonnelDto {

	private String id;


	@NotEmpty(message = "personnel name should not empty")
	@Size(min = 2, message = "personnel name should have at least 2 characters")
	private String nom;

	@Size(min = 2, message = "personnel name should have at least 2 characters")
	@NotEmpty(message = "personnel prenom should not empty")
	private String prenom;


	@Size(min = 2, message = "personnel adresse should have at least 2 characters")
	@NotEmpty(message = "personnel adresse should not empty")
	private String adresse;


	private String photo;

	private String ville;


	private String codePostal;

	@NotBlank
	@Size(min = 8, max = 8, message = "Input must be exactly 8 characters")
	@Indexed(unique = true)
	private String cin;

	@Email(message = "email is not valid")
	private String email;



	private String sexe;



	private String fullName;

	private String rib;


	private String poste;

	@NotNull(message = "personnel date embauche should not empty")
	private Date dateEmbauche;


	@NotNull(message = "personnel date Naissance should not empty")
	private Date dateNaissance;



	private String echelon;


	private String numCnss;


	private String situationFamiliale;


	private String nbrEnfant;



	private String typeContrat;

	private String matricule;

	private int counter;

	private String phone;

	private String categorie;



	private boolean miseEnVeille ;

	private List<Picture> pictures;

}
