package com.housservice.housstock.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "MetaData")
public class MetaData {

	@Transient
    public static final String SEQUENCE_NAME = "meta_data_sequence";

    @Id
    private String id;

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

}
