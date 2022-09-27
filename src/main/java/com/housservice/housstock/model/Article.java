package com.housservice.housstock.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String referenceIris;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String numFicheTechnique;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String prix;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String designation;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String typeProduit;

		private Client client ;
	private String refClient ;

private  Picture picture ;

private  List<EtapeProduction> etapeProductions ;


	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate dateCreationArticle = LocalDate.now();
	private int miseEnVeille;


}