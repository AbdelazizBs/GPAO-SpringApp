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
	@JsonFormat(pattern="dd/MM/yyyy")
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
	private String echelon;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String category;

	private Comptes compte;



}
