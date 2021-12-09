package com.housservice.housstock.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="CommandeClient")
public class CommandeClient {
	
	@Transient
	public static final String SEQUENCE_NAME ="commandeClient_sequence";
	
	@Id
	private String id;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String type_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String num_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String etat;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate  date_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date_creation_cmd;
	
	private Client client;

	private List<LigneCommandeClient> ligneCommandeClients = new ArrayList<>();
	


}
