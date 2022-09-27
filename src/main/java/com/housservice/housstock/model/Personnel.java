package com.housservice.housstock.model;

import java.time.LocalDate;

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
	public static final String SEQUENCE_NAME = "personne_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String cin;
	
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
	private String sexe;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateDeNaissance;
		

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
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
	private LocalDate dateDeEmbauche;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String echelon;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String categorie;
	

}