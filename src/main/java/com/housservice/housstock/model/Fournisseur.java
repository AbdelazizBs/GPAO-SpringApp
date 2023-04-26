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

	private String refFournisseurIris;
	private String statut;
	private String codePostal;
	private String adresse;
	private String ville;
	private String region;
	private String pays;
	private String codeDouane;
	private String rne;
	private String identifiantTva;





	private Date date ;


	private String raisonSocial;



	private String regime;


	private String abrege;


	private String linkedin;





	private String siteWeb;


	private String telecopie;



	private String phone;




	private String nomBanque;


	private String adresseBanque;


	private String rib;



	private String swift;


	private String email;




	private boolean miseEnVeille;


	private Date dateMiseEnVeille;


	private List <Contact> contact;
	private List <Picture> pictures;
	private int rate;

	public Fournisseur(String id, String refFournisseurIris, String statut, String codePostal, String adresse, String ville, String region, String pays, String codeDouane, String rne, String identifiantTva, Date date, String raisonSocial, String regime, String abrege, String linkedin, String siteWeb, String telecopie, String phone, String nomBanque, String adresseBanque, String rib, String swift, String email, boolean miseEnVeille, Date dateMiseEnVeille , List<Contact> contact, List<Picture> pictures,int rate) {
		this.id = id;
		this.refFournisseurIris = refFournisseurIris;
		this.statut = statut;
		this.codePostal = codePostal;
		this.adresse = adresse;
		this.ville = ville;
		this.region = region;
		this.pays = pays;
		this.codeDouane = codeDouane;
		this.rne = rne;
		this.identifiantTva = identifiantTva;
		this.date = date;
		this.raisonSocial = raisonSocial;
		this.regime = regime;
		this.abrege = abrege;
		this.linkedin = linkedin;
		this.siteWeb = siteWeb;
		this.telecopie = telecopie;
		this.phone = phone;
		this.nomBanque = nomBanque;
		this.adresseBanque = adresseBanque;
		this.rib = rib;
		this.swift = swift;
		this.email = email;
		this.miseEnVeille = miseEnVeille;
		this.dateMiseEnVeille = dateMiseEnVeille;
		this.contact = contact;
		this.pictures = pictures;
		this.rate = rate;
	}

	public Fournisseur(Date date, String refFournisseurIris, String raisonSocial, String regime,
                       String abrege, String linkedin,
                       String siteWeb,
                       String phone, String telecopie,
                       String nomBanque, String adresseBanque, String rib,
                       String swift, String email, boolean miseEnVeille) {

		this.refFournisseurIris = refFournisseurIris;
		this.date = date;
		this.raisonSocial = raisonSocial;
		this.regime = regime;
		this.abrege = abrege;
		this.linkedin = linkedin;
		this.telecopie = telecopie;
		this.phone = phone;
		this.email = email;
		this.siteWeb = siteWeb;
		this.nomBanque = nomBanque;
		this.adresseBanque = adresseBanque;
		this.rib = rib;
		this.swift = swift;
		this.miseEnVeille = miseEnVeille;
		this.contact = contact;

	}

	public Fournisseur() {

	}
}