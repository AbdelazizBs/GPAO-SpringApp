package com.housservice.housstock.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="LigneCommandeClient")
public class LigneCommandeClient{
	@Transient
	public static final String SEQUENCE_NAME ="ligneCommandeClient_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String quantite;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String prixUnitaire;
	
	private Article article;
	
	private CommandeClient commandeClient;

	private Date delai;

}
