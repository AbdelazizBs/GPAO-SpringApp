package com.housservice.housstock.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Ventes;

import lombok.Data;

@Data
public class LigneVenteDto {

	@Id
	private String id;
	
	@Size(max = 100)
	private BigDecimal quantite;
	
	@Size(max = 100)
	private BigDecimal prixUnitaire;
	
	private VentesDto vente;
	
	private ClientDto client;
	
}
