package com.housservice.housstock.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;


@Document(collection="CommandeClient")
public class CommandeClient {
	
	@Transient
	public static final String SEQUENCE_NAME ="commandeClient_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	
	@Size(max = 100)
	@Indexed(unique = true)
	private String type_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String num_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String etat;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate  date_cmd;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date_creation_cmd;

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
	 * @return the type_cmd
	 */
	public String getType_cmd() {
		return type_cmd;
	}

	/**
	 * @param type_cmd the type_cmd to set
	 */
	public void setType_cmd(String type_cmd) {
		this.type_cmd = type_cmd;
	}

	/**
	 * @return the num_cmd
	 */
	public String getNum_cmd() {
		return num_cmd;
	}

	/**
	 * @param num_cmd the num_cmd to set
	 */
	public void setNum_cmd(String num_cmd) {
		this.num_cmd = num_cmd;
	}

	/**
	 * @return the etat
	 */
	public String getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(String etat) {
		this.etat = etat;
	}

	/**
	 * @return the date_cmd
	 */
	public LocalDate getDate_cmd() {
		return date_cmd;
	}

	/**
	 * @param date_cmd the date_cmd to set
	 */
	public void setDate_cmd(LocalDate date_cmd) {
		this.date_cmd = date_cmd;
	}

	/**
	 * @return the date_creation_cmd
	 */
	public LocalDate getDate_creation_cmd() {
		return date_creation_cmd;
	}

	/**
	 * @param date_creation_cmd the date_creation_cmd to set
	 */
	public void setDate_creation_cmd(LocalDate date_creation_cmd) {
		this.date_creation_cmd = date_creation_cmd;
	}
	


}
