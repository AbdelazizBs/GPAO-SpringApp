package com.housservice.housstock.model.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.LigneCommandeFournisseur;

import lombok.Data;

@Data
public class CommandeFournisseurDto {
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String code;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private Instant dateCommande;
	

	private FournisseurDto fournisseur;
	

	private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs = new ArrayList<>();

}
