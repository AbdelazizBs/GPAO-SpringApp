package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.Date;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandeClientDto {
	
	@Id
	private String id;
		
	@Size(max = 100)
	private String typeCmd;
	
	@Size(max = 100)
	private String numCmd;
	
	@Size(max = 100)
	private String etat;

	private String etatProduction;

	private Date dateCmd;
	
	private LocalDate dateCreationCmd;
	
	private String idClient;

		private String raisonSocialClient;

		private Boolean haveLc;


}
