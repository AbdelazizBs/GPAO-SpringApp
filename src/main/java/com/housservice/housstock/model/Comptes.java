package com.housservice.housstock.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
@Document(collection = "Comptes")
public class Comptes {

	@Transient
    public static final String SEQUENCE_NAME = "comptes_sequence";
	
    @Id
    private String id;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String email;
    
    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String password;
    
	private Entreprise entreprise;
	
//	private Utilisateur utilisateur;
    
}
