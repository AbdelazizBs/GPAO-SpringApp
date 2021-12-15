package com.housservice.housstock.model.dto;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ComptesDto {
	
	    @Id
	    private String id;

	    @Size(max = 100)
	    private String raisonSocial;
	    
	    @Size(max = 100)
	    private String siren;
}
