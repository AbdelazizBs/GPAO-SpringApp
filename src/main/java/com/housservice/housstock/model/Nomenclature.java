package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Document(collection = "Nomenclature")
public class Nomenclature {
	
	@Transient
    public static final String SEQUENCE_NAME = "nomenclature_sequence";
	
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
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the idCompte
	 */
	public String getIdCompte() {
		return idCompte;
	}

	/**
	 * @param idCompte the idCompte to set
	 */
	public void setIdCompte(String idCompte) {
		this.idCompte = idCompte;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
