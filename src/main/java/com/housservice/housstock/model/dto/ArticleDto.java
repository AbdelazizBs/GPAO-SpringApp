package com.housservice.housstock.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ArticleDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String referenceIris;
	

	@Size(max = 100)
	private String numFicheTechnique;
	

	@Size(max = 100)
	private String designation;


	private List<EtapeProduction> etapeProductions ;


	@Size(max = 100)
	private String typeProduit;


	private String idClient ;
	private String refClient ;
	private String raisonSocial ;
	private String prix ;
	private int miseEnVeille;

	private Picture picture ;

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateCreationArticle = LocalDate.now();
	
		
}
