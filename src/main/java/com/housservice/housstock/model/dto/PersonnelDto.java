package com.housservice.housstock.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelDto extends PersonneDto{
	
	@Size(max = 100)
	private String poste;

	@Size(max = 100)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateDeEmbauche;
	
	@Size(max = 100)
	private String echelon;
	
	@Size(max = 100)
	private String categorie;

}
