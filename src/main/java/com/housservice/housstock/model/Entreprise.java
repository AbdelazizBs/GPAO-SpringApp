package com.housservice.housstock.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="Entreprise")
public class Entreprise{
	
	@Transient
	public static final String SEQUENCE_NAME ="entreprise_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String raisonSocial;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String description;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String codeFiscal;
		
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String numTel;
	
	private Set<String> listIdEtapeProductions = new HashSet<>();
	
	//private Set<String> listIdPersonnel = new HashSet<>();
		
	/*
	 * private Comptes compte;
	 * 
	 * private List<Personnel> personnels = new ArrayList<>();
	 * 
	 * private List<Client> clients = new ArrayList<>();
	 * 
	 * private List<Fournisseur> fournisseurs = new ArrayList<>();
	 * 
	 * private List<Article> articles = new ArrayList<>();
	 * 
	 * private List<EtapeProduction> etapeProductions = new ArrayList<>();
	 * 
	 * private List<Ventes> ventes = new ArrayList<>();
	 * 
	 * private List<Machine> machines = new ArrayList<>();
	 */
}
