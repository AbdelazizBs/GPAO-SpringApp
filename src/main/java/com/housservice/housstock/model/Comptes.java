package com.housservice.housstock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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

	
    @Id
    private String id;


    private String email;

    private String password;


    private List<Roles> roles ;

    private String idPersonnel;
    private String personnelName;
    private Date datelastlogin;

    private  boolean enVeille;

    public Comptes() {

    }
}
