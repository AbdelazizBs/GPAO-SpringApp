package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="Entreprise")
public class Entreprise{
	
	@Transient
	public static final String SEQUENCE_NAME ="entreprise_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String raisonSocial;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String description;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String codeFiscal;
	
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
		
	private Comptes compte;
	
	private List<Utilisateur> utilisateurs = new ArrayList<>();

}
