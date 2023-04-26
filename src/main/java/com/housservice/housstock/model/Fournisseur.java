package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Document(collection="Fournisseur")
public class Fournisseur {

	@Transient
	public static final String SEQUENCE_NAME ="fournisseur_sequence";
	
	@Id
	private String id;
	
	private String refFrsIris;
	
	private String intitule;
	
	private String abrege;
	
	private String statut;
		
	private String adresse;
	
	private String codePostal;
	
	private String ville;
	
	private String region;
	
	private String pays;
	
	private String telephone;
	
	private String telecopie;
	
	private String linkedin;
	
	private String email;
	
	private String siteWeb;
	
	private String nomBanque;
	
	private String adresseBanque;
	
	private String rib;
	
	private String swift;
	
	private String codeDouane;
	
	private String rne;
	
	private String identifiantTva;
		
	private int miseEnVeille;

	private Date dateMiseEnVeille;
	
	private List <Contact> contact;
	
	private List <Picture> pictures;
	
	private Date date ;
	

	public Fournisseur() {

	}
	
	public Fournisseur(String refFrsIris, String intitule, String adresse, String codePostal, String ville, String region,String pays,
			 String telephone, String telecopie, String email,String nomBanque,String adresseBanque,String rib,
					   String swift,String codeDouane,String rne,String identifiantTva, int miseEnVeille ,  List<Contact> contact , Date date) {

		this.refFrsIris = refFrsIris;
		this.intitule = intitule;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.region = region;
		this.pays = pays;
		this.telephone = telephone;
		this.telecopie = telecopie;
		this.email = email;
		this.nomBanque = nomBanque;
		this.adresseBanque = adresseBanque;
		this.rib = rib;
		this.swift = swift;
		this.codeDouane = codeDouane;
		this.rne = rne;		
		this.identifiantTva = identifiantTva;
		this.miseEnVeille = miseEnVeille;
		this.contact = contact;
		this.date = date ;
	}



}
