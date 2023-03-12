package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Matiere Primaire")
public class MatierePrimaire {
    @Transient
    public static final String SEQUENCE_NAME ="article_sequence";


    @Id
    private String id;
    private  String refMatiereiris;
    private String designation;
    private String famille;
    private String uniteAchat;
}
