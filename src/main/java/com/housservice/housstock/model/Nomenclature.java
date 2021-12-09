package com.housservice.housstock.model;

import java.util.HashSet;
import java.util.Set;

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
@Document(collection = "Nomenclature")
public class Nomenclature {
	
	@Transient
    public static final String SEQUENCE_NAME = "nomenclature_sequence";
	
	/**
	 * TYPE NOMENCLATURE : F
	 */
	@Transient
	public static final String TYPE_FAMILLE = "F";
	/**
	 * TYPE NOMENCLATURE : A
	 */
	@Transient
	public static final String TYPE_ARTICLE = "A";
	/**
	 * TYPE NOMENCLATURE : E
	 */
	@Transient
	public static final String TYPE_ELEMENT = "E";
	
    @Id
    private String id;

    @NotBlank
    private String idCompte;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String nom;
    

    private String description;
    
    @NotBlank
    private String type;
    
    private String idParent;
    
    private Set<String> listIdChildren = new HashSet<>();
    
}
