package com.housservice.housstock.model.dto;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComptesDto {
	
	    @Id
	    private String id;

	    @Size(max = 100)
	    private String email;
	    
	    @Size(max = 100)
	    private String password;
	    
	    private String idEntreprise;
		
		private String raisonSocialEntreprise;
		
//		private String idUtilisateur;
		
//		private String nomUtilisateur;
}
