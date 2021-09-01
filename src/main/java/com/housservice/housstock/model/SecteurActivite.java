package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="SecteurActivite")
public class SecteurActivite {
	
	@Transient
	public static final String SEQUENCE_NAME ="secteurActivite_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String secteur;

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
	 * @return the secteur
	 */
	public String getSecteur() {
		return secteur;
	}

	/**
	 * @param secteur the secteur to set
	 */
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	
	
}
