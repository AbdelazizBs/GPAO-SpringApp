package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class LigneCommandeClientDto {
	@Id
	private String id;
	
	@Size(max = 100)
	private String quantite;

	
	private String idNomenclature;

	private String idCommandeClient;

	private String numCmdClient;

	private Date delai;
}
