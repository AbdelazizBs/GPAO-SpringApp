package com.housservice.housstock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String refClientIris;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="yyyy/dd/MM")
	private Date date ;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String raisonSocial;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String regime;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String secteurActivite;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String brancheActivite;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresseFacturation;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresseLivraison;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String incoterm;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String telecopie;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String phone;
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String echeance;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String modePaiement;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nomBanque;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresseBanque;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String rib;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String swift;
	
	@NotBlank
	@Email(message = "email is not valid")
	private String email;



	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private boolean miseEnVeille;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private Date dateMiseEnVeille;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private boolean blocage;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private Date dateBlocage;


	private List <Contact> contact;

	private List<CommandeClient> listCommandes = new ArrayList<>();

	public Client(Date date,String refClientIris, String raisonSocial, String regime,
				  String secteurActivite, String brancheActivite,
				  String adresseFacturation, String adresseLivraison,
				  String incoterm, String echeance, String modePaiement,
				  String phone, String telecopie, 
				  String nomBanque, String adresseBanque, String rib,
				  String swift,String email, Date dateMiseEnVeille, boolean miseEnVeille,boolean blocage,Date dateBlocage,
				  List<Contact> contact, List<CommandeClient> listCommandes) {
		
		this.refClientIris = refClientIris;
		this.date = date;
		this.raisonSocial = raisonSocial;
		this.regime = regime;
		this.secteurActivite = secteurActivite;
		this.brancheActivite = brancheActivite;
		this.adresseFacturation = adresseFacturation;
		this.adresseLivraison = adresseLivraison;
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
		this.dateMiseEnVeille = dateMiseEnVeille;
		this.miseEnVeille = miseEnVeille;
		this.blocage = blocage;
		this.dateBlocage = dateBlocage;
		this.contact = contact;
		this.listCommandes = listCommandes;
		
	}

	public Client() {

	}
}
