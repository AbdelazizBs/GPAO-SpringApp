package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Matiere")
public class Matiere {
    @Transient
    public static final String SEQUENCE_NAME ="article_sequence";


    @Id
    private String id;
    private String designation;


    public Matiere(String id, String designation) {
        this.id = id;
        this.designation = designation;
    }
}
