package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.housservice.housstock.model.Utilisateur;

public class EntrepriseDto {

	@Id
	private String id;
	
	@Size(max = 100)
	private String nom;
	
	@Size(max = 100)
	private String description;
	
	@Size(max = 100)
	private String adresse;
	
	@Size(max = 100)
	private String codeFiscal;
	
	@Size(max = 100)
	private String photo;
	
	@Size(max = 100)
	private String email;
	
	@Size(max = 100)
	private String numTel;
		
	@NotBlank
	private String idCompte;
	
	private List<UtilisateurDto> utilisateurs = new ArrayList<>();
}
