package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;


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
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateCmd;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateCreationCmd;
	
	private String idClient;

		private String raisonSocialClient;

	//private List<LigneCommandeClientDto> ligneCommandeClients = new ArrayList<>();
}
