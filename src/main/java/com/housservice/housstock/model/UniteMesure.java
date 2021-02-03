package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;

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
@Document(collection = "UniteMesure")
public class UniteMesure {
	
	@Transient
    public static final String SEQUENCE_NAME = "unite_sequence";
	
    @Id
    private long id;

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

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
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

	/**
	 * @return the listMultiple
	 */
	public List<UniteMesureDetail> getListMultiple() {
		return listMultiple;
	}

	/**
	 * @param listMultiple the listMultiple to set
	 */
	public void setListMultiple(List<UniteMesureDetail> listMultiple) {
		this.listMultiple = listMultiple;
	}

	/**
	 * @return the listSousMultiple
	 */
	public List<UniteMesureDetail> getListSousMultiple() {
		return listSousMultiple;
	}

	/**
	 * @param listSousMultiple the listSousMultiple to set
	 */
	public void setListSousMultiple(List<UniteMesureDetail> listSousMultiple) {
		this.listSousMultiple = listSousMultiple;
	}
    
    
}
