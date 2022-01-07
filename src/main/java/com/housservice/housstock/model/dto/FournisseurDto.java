package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
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

	@Size(max = 100)
	private String raisonSocial;

	@Size(max = 100)
	private String adresse;

	@Size(max = 100)
	private String modePaiement;

	@Size(max = 100)
	private String email;
	
	@Size(max = 100)
	private String numTel;

	private List<CommandeFournisseurDto> listCommandes = new ArrayList<>();
	
}
