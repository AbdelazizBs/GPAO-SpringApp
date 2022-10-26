package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Roles;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilisateurDto {

	@Id
	private String id;

	@Size(max = 100)
	private String nom;

	@Size(max = 100)
	private String prenom;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateDeNaissance;

	@Size(max = 100)
	private String adresse;
	
	@Size(max = 100)
	private String photo;
	
//
//    private String idEntreprise;
//
//	private String raisonSocialEntreprise;
//
	
    private Comptes comptes;
	

	
	private List<Roles> roles = new ArrayList<>();
	
}
