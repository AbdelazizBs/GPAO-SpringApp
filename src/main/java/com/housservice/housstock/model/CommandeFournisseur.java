package com.housservice.housstock.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Document(collection="CommandeFournisseur")
public class CommandeFournisseur{
	
	@Transient
	public static final String SEQUENCE_NAME ="commandeFournisseur_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String code;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate  dateCommande;
	
	private Fournisseur fournisseur;
	

	private List<LigneCommandeFournisseur> ligneCommandeFournisseurs = new ArrayList<>();

}