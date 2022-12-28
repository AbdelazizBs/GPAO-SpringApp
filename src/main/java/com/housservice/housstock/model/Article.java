package com.housservice.housstock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

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
	private String raisonSocial ;

private  Picture picture ;

private  List<EtapeProduction> etapeProductions ;



	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate dateCreationArticle = LocalDate.now();
	private int miseEnVeille;


}
