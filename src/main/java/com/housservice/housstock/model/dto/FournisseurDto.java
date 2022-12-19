package com.housservice.housstock.model.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FournisseurDto {
	
	private String id;

	private String refFrsIris;

	private String intitule;

	private String abrege;

	private String interlocuteur;
	
	private String adresse;

	private String codePostal;

	private String ville;

	private String region;
	
	private String pays;
	
	private String telephone;
	
	private String telecopie;
	
	private String linkedin;
	
	private String email;
	
	private String siteInternet;
	
	private String identifiantTva;

	private boolean miseEnVeille;

	private Date dateMiseEnVeille;
		
	
}
