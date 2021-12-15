package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Entreprise;
import com.housservice.housstock.model.Roles;

import lombok.Data;

@Data
public class UtilisateurDto {

	@Id
	private String id;

	@Size(max = 100)
	private String nom;

	@Size(max = 100)
	private String prenom;

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateDeNaissance;

	@Size(max = 100)
	private String adresse;
	
	@Size(max = 100)
	private String photo;
	

	private EntrepriseDto entreprise;
	
	
	private ComptesDto compte;
	
	
	private List<RolesDto> role = new ArrayList<>();
	
}
