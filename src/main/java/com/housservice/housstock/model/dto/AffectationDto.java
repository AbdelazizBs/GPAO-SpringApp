package com.housservice.housstock.model.dto;


import com.housservice.housstock.model.PrixAchat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AffectationDto {
	
	@Id
	private String id;
	@NotNull
	private Date dateeffect;
	private String ref;
	@NotEmpty
	private String devises;
	@NotNull
	private int prix;
	@NotNull
	private int minimumachat;
	@NotEmpty
	private String listFournisseur;
	private String unite;
	@NotEmpty
	private String uniteAchat;
	private String destination;
	private String type;

	private String idmatiere;
	private List<PrixAchat> prixAchat;



}
