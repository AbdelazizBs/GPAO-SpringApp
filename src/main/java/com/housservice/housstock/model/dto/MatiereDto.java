package com.housservice.housstock.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereDto {
	
	private String id;
	
	private String refMatiereIris;

	private String designation;
	
	private String famille;

	private String uniteAchat;

	private BigDecimal prixUnitaire;
	
	private boolean miseEnVeille ;
	

}
