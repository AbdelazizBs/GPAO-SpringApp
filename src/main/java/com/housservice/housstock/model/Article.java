package com.housservice.housstock.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection="Article")
public class Article{
	
	@Transient
	public static final String SEQUENCE_NAME ="article_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String codeArticle;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String designation;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal prixUnitaireHt;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal tauxTva;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal prixUnitaireTtc;
	
	
	private Categorie categorie;
	
}
