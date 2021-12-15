package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;


/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Data
@Builder
public class ClientDto {
	
	@Id
	private String id;

	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate date = LocalDate.now();
	

	@Size(max = 100)
	private String raisonSocial;
	  
	@Size(max = 100)
	private String regime;
	
	@Size(max = 100)
	private String secteurActivite;
	
	@Size(max = 100)
	private String brancheActivite;
	
	
	@Size(max = 100)
	private String adresseFacturation;
	
    
	@Size(max = 100)
	private String adresseLivraison;
	
	
	@Size(max = 100)
	private String incoterm;

    
	@Size(max = 100)
	private String echeance;
	
	
	@Size(max = 100)
	private String modePaiement;
	
    
	@Size(max = 100)
	private String nomBanque;
	
	@Size(max = 100)
	private String adresseBanque;
	
    
	@Size(max = 100)
	private String rib;
	
	
	@Size(max = 100)
	private String swift;
	
    
	@Size(max = 100)
	private int blocage;
	
	
	@Size(max = 100)
	private int miseEnVeille;
	

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateMiseEnVeille;
	
	
	private List<CommandeClientDto> listCommandes = new ArrayList<>();
	
    
}
