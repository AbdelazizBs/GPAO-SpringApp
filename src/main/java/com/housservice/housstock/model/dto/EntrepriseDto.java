package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntrepriseDto {

	@Id
	private String id;
	
	@Size(max = 100)
	private String raisonSocial;
	
	@Size(max = 100)
	private String description;
	
	@Size(max = 100)
	private String adresse;
	
	@Size(max = 100)
	private String codeFiscal;
		
	@Size(max = 100)
	private String email;
	
	@Size(max = 100)
	private String numTel;

	private String idCompte;
	
	private String raisonSocialCompte;
	
	private List<UtilisateurDto> utilisateurs = new ArrayList<>();
}
