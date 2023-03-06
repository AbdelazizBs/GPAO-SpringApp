package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.LigneCommandeClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommandeClientDto {
	
	@Id
	private String id;
		
	@Size(max = 100)
	private String typeCmd;
	
	@Size(max = 100)
	private String numCmd;

	private boolean closed;


	private String etatProduction;

	private Date dateCmd;
	
	private Date dateCreationCmd;
	
	private String idClient;

		private String raisonSocialClient;
	private List<LigneCommandeClient> ligneCommandeClient;


	private Boolean haveLc;


}
