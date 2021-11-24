package com.housservice.housstock.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class EtapeProductionDto {
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String nom_etape;
	
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String type_etape;
	
	   
}
