package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="Utilisateur")
public class Utilisateur {
	
	@Transient
	public static final String SEQUENCE_NAME ="utilisateur_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nom;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String prenom;
	
	 
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	
   
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}
    
	
	/**
	 * @return the prenom
	 */
	public String getPrenom(){
		return prenom;
	}
    
	
	/**
	 * @param nom the prenom to set
	 */
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

}
