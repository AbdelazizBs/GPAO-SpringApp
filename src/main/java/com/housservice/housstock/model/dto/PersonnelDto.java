package com.housservice.housstock.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelDto{
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String cin;

	@Size(max = 100)
	private String nom;

	@Size(max = 100)
	private String prenom;
	
	@Size(max = 100)
	private String sexe;

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateDeNaissance;
	

	@Size(max = 100)
	private String adresse;

	
	@Size(max = 100)
	private String rib;
	
	@Size(max = 100)
	private String poste;

	@Size(max = 100)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateDeEmbauche;
	
	@Size(max = 100)
	private String echelon;
	
	@Size(max = 100)
	private String categorie;

}
