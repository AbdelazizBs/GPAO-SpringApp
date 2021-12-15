package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Data
@Document
public class UniteMesureDetail {
	@Transient
    public static final String SEQUENCE_NAME = "unite_sequence";

    @Id
    private String id;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String nom;
    
    @NotBlank
    @Size(max = 1)
    private String type;

    private double coefficient;

       
}
