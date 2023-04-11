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
@Document(collection="Client")
public class Client {

	@Transient
	public static final String SEQUENCE_NAME ="client_sequence";

	@Id
	private String id;

	private String refClientIris;
	private String statut;
	private String codePostal;
	private String adresse;
	private String ville;
	private String region;
	private String pays;
	private String codeDouane;
	private String rne;
	private String cif;





	private Date date ;


	private String raisonSocial;



	private String regime;


	private String secteurActivite;


	private String brancheActivite;





	private String incoterm;


	private String telecopie;



	private String phone;

	private String echeance;


	private String modePaiement;


	private String nomBanque;


	private String adresseBanque;


	private String rib;



	private String swift;


	private String email;




	private boolean miseEnVeille;


	private Date dateMiseEnVeille;


	private int blocage;

	private Date dateBlocage;


	private List <Contact> contact;
	private List <Picture> pictures;
	private int rate;
	public Client(String id, String refClientIris, String statut, String codePostal, String adresse, String ville, String region, String pays, String codeDouane, String rne, String cif, Date date, String raisonSocial, String regime, String secteurActivite, String brancheActivite, String incoterm, String telecopie, String phone, String echeance, String modePaiement, String nomBanque, String adresseBanque, String rib, String swift, String email, boolean miseEnVeille, Date dateMiseEnVeille, int blocage, Date dateBlocage, List<Contact> contact, List<Picture> pictures,int rate) {
		this.id = id;
		this.refClientIris = refClientIris;
		this.statut = statut;
		this.codePostal = codePostal;
		this.adresse = adresse;
		this.ville = ville;
		this.region = region;
		this.pays = pays;
		this.codeDouane = codeDouane;
		this.rne = rne;
		this.cif = cif;
		this.date = date;
		this.raisonSocial = raisonSocial;
		this.regime = regime;
		this.secteurActivite = secteurActivite;
		this.brancheActivite = brancheActivite;
		this.incoterm = incoterm;
		this.telecopie = telecopie;
		this.phone = phone;
		this.echeance = echeance;
		this.modePaiement = modePaiement;
		this.nomBanque = nomBanque;
		this.adresseBanque = adresseBanque;
		this.rib = rib;
		this.swift = swift;
		this.email = email;
		this.miseEnVeille = miseEnVeille;
		this.dateMiseEnVeille = dateMiseEnVeille;
		this.blocage = blocage;
		this.dateBlocage = dateBlocage;
		this.contact = contact;
		this.pictures = pictures;
		this.rate=rate;
	}

	public Client(Date date, String refClientIris, String raisonSocial, String regime,
				  String secteurActivite, String brancheActivite,
				  String incoterm, String echeance, String modePaiement,
				  String phone, String telecopie,
				  String nomBanque, String adresseBanque, String rib,
				  String swift, String email, boolean miseEnVeille, int blocage) {

		this.refClientIris = refClientIris;
		this.date = date;
		this.raisonSocial = raisonSocial;
		this.regime = regime;
		this.secteurActivite = secteurActivite;
		this.brancheActivite = brancheActivite;
		this.telecopie = telecopie;
		this.phone = phone;
		this.email = email;
		this.incoterm = incoterm;
		this.echeance = echeance;
		this.modePaiement = modePaiement;
		this.nomBanque = nomBanque;
		this.adresseBanque = adresseBanque;
		this.rib = rib;
		this.swift = swift;
		this.miseEnVeille = miseEnVeille;
		this.blocage = blocage;


	}

	public Client() {

	}
}