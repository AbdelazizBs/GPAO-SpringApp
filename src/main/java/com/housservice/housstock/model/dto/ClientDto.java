package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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

	private String refClientIris;
	
	private Date date ;
	
	private String raisonSocial;
	
	private String telecopie;
	
	private String phone;
	
	private String regime;
	
	private String secteurActivite;
	
	private String brancheActivite;
	
	private String adresseFacturation;
	
	private String adresseLivraison;
		
	private String incoterm;
 
	private String echeance;
		
	private String modePaiement;
	  
	private String nomBanque;
	
	private String adresseBanque;
	   
	private String rib;
		
	private String swift;
	
	private String email;
	  
	private int miseEnVeille;

	private Date dateMiseEnVeille;
	
	private int blocage;
	
	private Date dateBlocage;
	
	private List<CommandeClientDto> listCommandes = new ArrayList<>();

	private List<Contact> contact;
    
}
