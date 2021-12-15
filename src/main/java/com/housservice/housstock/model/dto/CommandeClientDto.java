package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;

@Data
public class CommandeClientDto {
	
	@Id
	private String id;
		
	@Size(max = 100)
	private String type_cmd;
	
	@Size(max = 100)
	private String num_cmd;
	
	@Size(max = 100)
	private String etat;
	

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate  date_cmd;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date_creation_cmd;
	
	private ClientDto client;

	private List<LigneCommandeClientDto> ligneCommandeClients = new ArrayList<>();
}
