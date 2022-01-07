package com.housservice.housstock.model.dto;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesDto {

	@Id
	private String id;

	@Size(max = 100)
	private String libelle;


	private UtilisateurDto utilisateur;
	
}
