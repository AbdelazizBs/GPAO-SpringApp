package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Typepapier")
public class TypePapier {
    @Id
    private  String id;
    private String nom;

    public TypePapier(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public TypePapier() {
    }
}
