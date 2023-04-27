package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Etape")
public class Etape {
    @Transient
    public static final String SEQUENCE_NAME ="etape_sequence";


    @Id
    private String id;
    private String nomEtape;
    private String typeEtape;

    public Etape(String nomEtape, String typeEtape) {
        this.nomEtape = nomEtape;
        this.typeEtape = typeEtape;
    }
}
