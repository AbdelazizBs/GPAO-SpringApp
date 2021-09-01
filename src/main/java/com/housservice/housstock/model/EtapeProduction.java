package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="EtapeProduction")
public class EtapeProduction {
	
	@Transient
	public static final String SEQUENCE_NAME = "etapeProduction_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String nom_etape;
	
	@NotBlank
	@Size(max=100)
	@Indexed(unique = true)
	private String type_etape;
	
	
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
	 * @return the nom_etape
	 */
	public String getNom_etape() {
		return nom_etape;
	}

	/**
	 * @param nom_etape the nom_etape to set
	 */
	public void setNom_etape(String nom_etape) {
		this.nom_etape = nom_etape;
	}

	/**
	 * @return the type_etape
	 */
	public String getType_etape() {
		return type_etape;
	}

	/**
	 * @param type_etape the type_etape to set
	 */
	public void setType_etape(String type_etape) {
		this.type_etape = type_etape;
	}


}
