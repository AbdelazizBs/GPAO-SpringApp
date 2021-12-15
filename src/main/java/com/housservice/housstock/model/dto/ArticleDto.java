package com.housservice.housstock.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String codeArticle;

	@Size(max = 100)
	private String designation;
	
	@Size(max = 100)
	private BigDecimal prixUnitaireHt;

	@Size(max = 100)
	private BigDecimal tauxTva;
	
	@Size(max = 100)
	private BigDecimal prixUnitaireTtc;
	
	@Size(max = 100)
	private String photo;

	
    private String idCategorie;
	
	private String designationCategorie;
}
