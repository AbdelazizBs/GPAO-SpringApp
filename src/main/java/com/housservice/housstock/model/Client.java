package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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



	//private String regime;


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

	private List<CommandeClient> listCommandes = new ArrayList<>();

	public Client(Date date,String refClientIris, String raisonSocial, String statut,
				  String secteurActivite, String brancheActivite,
				  String incoterm, String echeance, String modePaiement,
				  String phone, String telecopie,
				  String nomBanque, String adresseBanque, String rib,
				  String swift,String email, boolean miseEnVeille,int blocage,
				  List<Contact> contact, List<CommandeClient> listCommandes) {

		this.refClientIris = refClientIris;
		this.date = date;
		this.raisonSocial = raisonSocial;
		//this.regime = regime;
		this.statut = statut;
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
		this.contact = contact;
		this.listCommandes = listCommandes;

	}

	public Client() {

	}
}