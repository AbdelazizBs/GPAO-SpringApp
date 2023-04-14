package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Devise")
public class Devise {
    @Id
    private  String id;
    private String nom;

    public Devise() {
    }

    public Devise(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
