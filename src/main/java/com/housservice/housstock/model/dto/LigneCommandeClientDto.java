package com.housservice.housstock.model.dto;

import java.util.Date;

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
	private String quantite;

	@Size(max = 100)
	private String prixUnitaire;
	
	private String idArticle;
		
	private String designationArticle;
	
	private String idCommandeClient;
	
	private String numCmdClient;
	private String refIris;


	private Date delai;
}
