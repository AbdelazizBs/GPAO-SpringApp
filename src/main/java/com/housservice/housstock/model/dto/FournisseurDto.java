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
public class FournisseurDto {

	@Id
	private String id;
	
	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate date = LocalDate.now();
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String reference;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String type;

	@Size(max = 100)
	private String raisonSocial;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String numTel;
		
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String banque;
	
	
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
	private String echeance;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String modePaiement;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String codeTva;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String exonerationRs;
		
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String certification;

	private List<CommandeFournisseurDto> listCommandes = new ArrayList<>();
	
}
