package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.housservice.housstock.model.CommandeClient;

import lombok.Getter;
import lombok.Setter;

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
	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate date = LocalDate.now();
	
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
	@Size(max = 100)
	@Indexed(unique = true)
	private int blocage;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int miseEnVeille;
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateMiseEnVeille;
	
	
	private List<CommandeClient> listCommandes = new ArrayList<>();
	
    
   // private List<ClientDto> listClientChildren = new ArrayList<>();
    
}
