package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Data
@Document(collection = "UniteMesure")
public class UniteMesure {
	
	@Transient
    public static final String SEQUENCE_NAME = "unite_sequence";
	
    @Id
    private String id;

    @NotBlank
    private String idCompte;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String nom;
    

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String label;
    
    private String description;
    
    private List<UniteMesureDetail> listMultiple = new ArrayList<>();

    private List<UniteMesureDetail> listSousMultiple = new ArrayList<>();
   
}
