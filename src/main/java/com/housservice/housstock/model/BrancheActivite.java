package com.housservice.housstock.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="BrancheActivite")
public class BrancheActivite {
	
	@Transient
	public static final String SEQUENCE_NAME ="brancheActivite_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String branche;

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
	 * @return the branche
	 */
	public String getBranche() {
		return branche;
	}

	/**
	 * @param branche the branche to set
	 */
	public void setBranche(String branche) {
		this.branche = branche;
	}
	

}
