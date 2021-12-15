package com.housservice.housstock.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VentesDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String code;


	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateVente;


	@Size(max = 100)
  	private String commentaire;

}
