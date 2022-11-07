package com.housservice.housstock.model.dto;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class DocumentDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String compteId;
	

	@Size(max = 100)
	private String categoroeDocument;
	

	@Size(max = 100)
	private String extensionDocument;


	private byte[] document ;


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
	 * @return the compteId
	 */
	public String getCompteId() {
		return compteId;
	}


	/**
	 * @param compteId the compteId to set
	 */
	public void setCompteId(String compteId) {
		this.compteId = compteId;
	}


	/**
	 * @return the categoroeDocument
	 */
	public String getCategoroeDocument() {
		return categoroeDocument;
	}


	/**
	 * @param categoroeDocument the categoroeDocument to set
	 */
	public void setCategoroeDocument(String categoroeDocument) {
		this.categoroeDocument = categoroeDocument;
	}


	/**
	 * @return the extensionDocument
	 */
	public String getExtensionDocument() {
		return extensionDocument;
	}


	/**
	 * @param extensionDocument the extensionDocument to set
	 */
	public void setExtensionDocument(String extensionDocument) {
		this.extensionDocument = extensionDocument;
	}


	/**
	 * @return the document
	 */
	public byte[] getDocument() {
		return document;
	}


	/**
	 * @param document the document to set
	 */
	public void setDocument(byte[] document) {
		this.document = document;
	}
	
	


}
