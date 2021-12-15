package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class FournisseurDto {

	@Id
	private String id;

	@Size(max = 100)
	private String nom;
	
	@Size(max = 100)
	private String prenom;

	@Size(max = 100)
	private String adresse;

	@Size(max = 100)
	private String photo;

	@Size(max = 100)
	private String email;
	
	@Size(max = 100)
	private String numTel;

	private List<CommandeFournisseurDto> commandeFournisseurs = new ArrayList<>();
}
