package com.housservice.housstock.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
@Document(collection="LigneCommandeClient")
public class LigneCommandeClient{
	@Transient
	public static final String SEQUENCE_NAME ="ligneCommandeClient_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal quantite;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal prixUnitaire;
	
	private Article article;
	
	private CommandeClient commandeClient;
	
}
