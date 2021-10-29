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
	private String raison_social;
	
    
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
	private String adresse_facturation;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse_liv;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String Incoterm;

    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String echeance;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String Mode_pai;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nom_banque;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse_banque;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String RIB;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String SWIFT;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int blocage;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int MiseEnVeille;
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date_MiseEnVeille;
	
	
	private List<CommandeClient> listCommandes = new ArrayList<>();
	
    
   // private List<ClientDto> listClientChildren = new ArrayList<>();
    
}
