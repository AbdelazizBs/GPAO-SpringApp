package com.housservice.housstock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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


    private String email;

    private String password;


    private List<Roles> roles ;


    public Comptes() {

    }
}
