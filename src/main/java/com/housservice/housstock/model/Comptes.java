package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Compte")
public class Comptes {

	@Transient
    public static final String SEQUENCE_NAME = "compte_sequence";
	
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


    private List<Roles> roles ;


    public Comptes() {

    }
}
