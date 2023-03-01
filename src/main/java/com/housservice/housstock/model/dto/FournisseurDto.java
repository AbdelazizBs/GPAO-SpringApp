package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class FournisseurDto {
	
	@Id
	private String id;


	@Size(min = 2, message = "fournisseur matricule should have at least 2 characters")
	@NotEmpty(message = "fournisseur matricule  should not empty")
	private String refFournisseurIris;


	private Date date ;


	@Size(min = 2, message = "fournisseur raisonSocial(intitule) should have at least 2 characters")
	@NotEmpty(message = "fournisseur raisonSocial(intitule)  should not empty")
	private String raisonSocial;



	@Size(min = 2, message = "fournisseur pays should have at least 2 characters")
	@NotEmpty(message = "fournisseur pays  should not empty")
	private String pays;

	private String regime;


	private String abrege;


	private String linkedin;


	@Size(min = 2, message = "fournisseur adresse should have at least 2 characters")
	@NotEmpty(message = "fournisseur adresse  should not empty")
	private  String adresse ;


	private String statut;


	@Size(min = 2, message = "fournisseur codePostal should have at least 2 characters")
	@NotEmpty(message = "fournisseur codePostal  should not empty")
	private String codePostal;



	@Size(min = 2, message = "fournisseur ville should have at least 2 characters")
	@NotEmpty(message = "fournisseur ville  should not empty")
	private String ville;


	private String region;


	private String codeDouane;



	private String rne;



	private String identifiantTva;



	private String siteWeb;

	private String telecopie;


	@Size(min = 2, message = "fournisseur phone should have at least 2 characters")
	@NotEmpty(message = "fournisseur phone  should not empty")
	private String phone;

	private String nomBanque;


	private String adresseBanque;


	private String rib;



	private String swift;

	@Email(message = "email is not valid")
	private String email;




	private boolean miseEnVeille;


	private Date dateMiseEnVeille;


	private List <Contact> contact;
	private List <Picture> pictures;


}
