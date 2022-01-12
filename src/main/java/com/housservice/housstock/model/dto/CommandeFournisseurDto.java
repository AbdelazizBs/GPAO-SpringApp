package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandeFournisseurDto {
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String code;
	
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate  dateCommande;
	

    private String idFournisseur;
	
	private String raisonSocialFournisseur;
	

	private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs = new ArrayList<>();

}
