package com.housservice.housstock.model.dto;


import java.util.Date;

import javax.validation.constraints.Size;

import com.housservice.housstock.model.EtatMachine;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Size(max = 100)
	private String reference;
    
	@Size(max = 100)
	private String libelle;
	
	@Size(max = 100)
	private int nbrConducteur;
	
	@JsonFormat(pattern="yyyy-MM-dd")	
	private Date dateMaintenance;
		
	private EtatMachine etatMachine;
private Boolean enVeille ;
	private String nomEtapeProduction;

    
}
