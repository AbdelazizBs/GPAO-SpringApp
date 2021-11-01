package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


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
	

}
