package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "UniteAchat")

public class UniteAchat {
    @Id
    private  String id;
    private String nom;

    public UniteAchat() {
    }

    public UniteAchat(String nom) {
        this.nom = nom;
    }

    public UniteAchat(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
