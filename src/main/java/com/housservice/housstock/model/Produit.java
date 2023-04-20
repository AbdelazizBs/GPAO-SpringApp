package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Document(collection="Produit")
public class Produit {
    @Id
    private String id;
    private String ref;
    private String type;
    private String designation;
    private Date dateCreation;

    public Produit(String ref, String type, String designation, Date dateCreation) {
        this.ref = ref;
        this.type = type;
        this.designation = designation;
        this.dateCreation = dateCreation;
    }

    public Produit() {
    }
}
