package com.housservice.housstock.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="Machine")
public class Machine {
	
	@Transient
	public static final String SEQUENCE_NAME ="machine_sequence";
	
	@Id
	private String id;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String reference;
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String libelle;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private EtatMachine etatMachine;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int nbrConducteur;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")	
	private Date dateMaintenance;
	private Boolean enVeille ;


	private EtapeProduction etapeProduction;
	

}
