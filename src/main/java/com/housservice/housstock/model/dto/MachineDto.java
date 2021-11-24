package com.housservice.housstock.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.housservice.housstock.model.EtapeProduction;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class MachineDto {
	
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
	private String nbrConducteur;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")	
	private LocalDate dateMaintenance;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private EtapeProduction etapeProduction;
    
}
