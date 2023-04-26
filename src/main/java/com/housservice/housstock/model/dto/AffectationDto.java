package com.housservice.housstock.model.dto;


import com.housservice.housstock.model.PrixAchat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AffectationDto {
	
	@Id
	private String id;
	private Date dateeffect;
	private String ref;
	private String devises;
	private int prix;
	private int minimumachat;
	private String listFournisseur;
	private String unite;
	private String uniteAchat;
	private String destination;

	private String idmatiere;
	private List<PrixAchat> prixAchat;



}
