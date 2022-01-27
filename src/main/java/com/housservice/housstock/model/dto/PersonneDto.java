package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonneDto {
	
	@Id
	private String id;

	@Size(max = 100)
	private String nom;

	@Size(max = 100)
	private String prenom;
	
	@Size(max = 100)
	private String sexe;


	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateDeNaissance;
	

	@Size(max = 100)
	private String cin;

	@Size(max = 100)
	private String adresse;

	@Size(max = 100)
	private String photo;
	
	@Size(max = 100)
	private String rib;
}
