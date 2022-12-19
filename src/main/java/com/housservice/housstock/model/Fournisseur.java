package com.housservice.housstock.model;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Email;
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
@Document(collection="Fournisseur")
public class Fournisseur {

	@Transient
	public static final String SEQUENCE_NAME ="fournisseur_sequence";
	
	@Id
	private String id;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String refFrsIris;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String intitule;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String abrege;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String interlocuteur;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String codePostal;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String ville;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String region;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String pays;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String telephone;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String telecopie;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String linkedin;
	
	@NotBlank
	@Email(message = "email is not valid")
	private String email;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String siteInternet;
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String identifiantTva;
		
	
	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate dateAjout = LocalDate.now();
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private boolean miseEnVeille;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private Date dateMiseEnVeille;
	

	public Fournisseur() {

	}
	
	public Fournisseur(String refFrsIris, String intitule, String abrege, String interlocuteur, String adresse, String codePostal, String ville, String region,String pays,
			 String telephone, String telecopie, String linkedin, String email, String siteInternet,String identifiantTva,LocalDate dateAjout, boolean miseEnVeille, Date dateMiseEnVeille) {

		this.refFrsIris = refFrsIris;
		this.intitule = intitule;
		this.abrege = abrege;
		this.interlocuteur = interlocuteur;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.region = region;
		this.pays = pays;
		this.telephone = telephone;
		this.telecopie = telecopie;
		this.linkedin = linkedin;
		this.email = email;
		this.siteInternet = siteInternet;
		this.identifiantTva = identifiantTva;
		this.dateAjout = dateAjout;
		this.miseEnVeille = miseEnVeille;
		this.dateMiseEnVeille = dateMiseEnVeille;
	}
}
