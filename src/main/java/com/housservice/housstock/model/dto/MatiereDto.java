package com.housservice.housstock.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String codeMatiere;

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

}
