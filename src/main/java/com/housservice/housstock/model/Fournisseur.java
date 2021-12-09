package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


@Data
@Builder 

@Document(collection="Fournisseur")
public class Fournisseur{
	
	@Transient
	public static final String SEQUENCE_NAME ="fournisseur_sequence";
	
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
	private String adresse;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String photo;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String numTel;

	private List<CommandeFournisseur> commandeFournisseurs = new ArrayList<>();
}
