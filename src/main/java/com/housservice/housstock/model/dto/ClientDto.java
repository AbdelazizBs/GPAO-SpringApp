package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class ClientDto {
	
	@Id
	private String id;


	@Size(min = 2, message = "client matricule should have at least 2 characters")
	@NotEmpty(message = "client matricule  should not empty")
	private String refClientIris;


	private Date date ;


	@Size(min = 2, message = "client raisonSocial(intitule) should have at least 2 characters")
	@NotEmpty(message = "client raisonSocial(intitule)  should not empty")
	private String raisonSocial;



	@Size(min = 2, message = "client pays should have at least 2 characters")
	@NotEmpty(message = "client pays  should not empty")
	private String pays;

	private String regime;


	private String secteurActivite;


	private String brancheActivite;


	@Size(min = 2, message = "client adresse should have at least 2 characters")
	@NotEmpty(message = "client adresse  should not empty")
	private  String adresse ;


	private String statut;


	@Size(min = 2, message = "client codePostal should have at least 2 characters")
	@NotEmpty(message = "client codePostal  should not empty")
	private String codePostal;



	@Size(min = 2, message = "client ville should have at least 2 characters")
	@NotEmpty(message = "client ville  should not empty")
	private String ville;


	private String region;


	private String codeDouane;



	private String rne;



	private String cif;



	private String incoterm;

	private String telecopie;


	@Size(min = 2, message = "client phone should have at least 2 characters")
	@NotEmpty(message = "client phone  should not empty")
	private String phone;

	private String echeance;



	private String modePaiement;



	private String nomBanque;


	private String adresseBanque;


	private String rib;



	private String swift;

	@Email(message = "email is not valid")
	private String email;




	private boolean miseEnVeille;


	private Date dateMiseEnVeille;


	private int blocage;


	private Date dateBlocage;


	private List <Contact> contact;
	private List <Picture> pictures;

	private List<CommandeClient> listCommandes = new ArrayList<>();
    
}
