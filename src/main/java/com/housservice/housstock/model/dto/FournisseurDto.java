package com.housservice.housstock.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FournisseurDto {

	@Id
	private String id;

	@Size(min = 2, message = "reference fournisseur should have at least 2 characters")
	@NotEmpty(message = "reference fournisseur should not empty")
	private String refFrsIris;

	@Size(min = 2, message = "Intitule fournisseur should have at least 2 characters")
	@NotEmpty(message = "Intitule fournisseur should not empty")
	private String intitule;

	private String abrege;

	private String statut;

	@Size(min = 2, message = "Fournisseur adresse should have at least 2 characters")
	@NotEmpty(message = "Fournisseur adresse  should not empty")
	private String adresse;

	@Size(min = 2, message = "Fournisseur codePostal should have at least 2 characters")
	@NotEmpty(message = "Fournisseur codePostal  should not empty")
	private String codePostal;

	@Size(min = 2, message = "Fournisseur ville should have at least 2 characters")
	@NotEmpty(message = "Fournisseur ville  should not empty")
	private String ville;

	private String region;

	@Size(min = 2, message = "Fournisseur pays should have at least 2 characters")
	@NotEmpty(message = "Fournisseur pays  should not empty")
	private String pays;

	@Size(min = 2, message = "Telephone fournisseur should have at least 2 characters")
	@NotEmpty(message = "Telephone fournisseur should not empty")
	private String telephone;

	private String telecopie;

	private String linkedin;

	@Email(message = "email is not valid")
	private String email;

	private String siteWeb;

	private String nomBanque;

	private String adresseBanque;

	private String rib;

	private String swift;

	private String codeDouane;

	private String rne;

	private String identifiantTva;

	private boolean miseEnVeille;

	private Date dateMiseEnVeille;

	private Date date ;

	private List <Contact> contact;

	private List <Picture> pictures;


}
