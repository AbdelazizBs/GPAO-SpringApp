package com.housservice.housstock.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MetaData")
public class MetaData {

	@Transient
    public static final String SEQUENCE_NAME = "meta_data_sequence";

    @Id
    private long id;

    private String catalogue;

    @Size(max = 100)
    @Indexed(unique = true)
    private String titre;
    
    private String description;
    
    private String image;

    private Object data;

    @Indexed(unique = true)
    private String url;

    public MetaData() {

    }

    public MetaData(String titre, String description, String image, String catalogue, String url) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.catalogue = catalogue;
        this.url = url;
    }


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
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
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
	 * @return the catalogue
	 */
	public String getCatalogue() {
		return catalogue;
	}

	/**
	 * @param catalogue the catalogue to set
	 */
	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
