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
@Document(collection="Matiere")
public class Matiere {
	
	@Transient
	public static final String SEQUENCE_NAME ="matiere_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String refMatiereIris;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String designation;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String famille;
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String uniteAchat;
	
	
	private boolean miseEnVeille ;
	
	
	public Matiere() {

	}


	public Matiere(String refMatiereIris,String designation,
			String famille,String uniteAchat
		) {
		super();
		this.refMatiereIris = refMatiereIris;
		this.designation = designation;
		this.famille = famille;
		this.uniteAchat = uniteAchat;
		
	}
	
	
	

}
