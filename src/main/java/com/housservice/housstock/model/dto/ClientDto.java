package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String refClientIris;


	private Date date ;


	private String raisonSocial;


	private String pays;

	private String regime;


	private String secteurActivite;


	private String brancheActivite;


	private  String adresse ;


	private String statut;

	private String codePostal;


	private String ville;


	private String region;


	private String codeDouane;



	private String rne;



	private String cif;



	private String incoterm;

	private String telecopie;



	private String phone;

	private String echeance;



	private String modePaiement;



	private String nomBanque;


	private String adresseBanque;


	private String rib;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String swift;

	@NotBlank
	@Email(message = "email is not valid")
	private String email;




	private int miseEnVeille;


	private Date dateMiseEnVeille;


	private int blocage;


	private Date dateBlocage;


	private List <Contact> contact;

	private List<CommandeClient> listCommandes = new ArrayList<>();
    
}
