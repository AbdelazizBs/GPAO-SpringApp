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
@Document(collection = "Comptes")
public class Comptes {

	@Transient
    public static final String SEQUENCE_NAME = "comptes_sequence";
	
    @Id
    private long id;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String raisonSocial;
    
    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String siren;

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
	 * @return the raisonSocial
	 */
	public String getRaisonSocial() {
		return raisonSocial;
	}

	/**
	 * @param raisonSocial the raisonSocial to set
	 */
	public void setRaisonSocial(String raisonSocial) {
		this.raisonSocial = raisonSocial;
	}

	/**
	 * @return the siren
	 */
	public String getSiren() {
		return siren;
	}

	/**
	 * @param siren the siren to set
	 */
	public void setSiren(String siren) {
		this.siren = siren;
	}
    
    
}
