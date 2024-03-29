package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="EtapeProduction")
public class EtapeProduction {
	
	@Transient
	public static final String SEQUENCE_NAME = "etapeProduction_sequence";
	
	@Id
	private String id;
		
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String nomEtape;
	
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String typeEtape;

	
}
