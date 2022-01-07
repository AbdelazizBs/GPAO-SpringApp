package com.housservice.housstock.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneCommandeClientDto {
	@Id
	private String id;
	
	@Size(max = 100)
	private BigDecimal quantite;

	@Size(max = 100)
	private BigDecimal prixUnitaire;
	
	private ArticleDto article;
	
	private CommandeClientDto commandeClient;
}
