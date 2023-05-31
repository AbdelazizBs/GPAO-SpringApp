package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection="Produit")
public class Produit {
    @Id
    private String id;
    private String ref;
    private int counter;

    private String type;
    private String designation;
    private Date dateCreation;
    private String[] Etapes;
    private List<Picture> pictures;
    private boolean miseEnVeille;


    public Produit( String type, String designation, Date dateCreation, List<Picture> pictures, boolean miseEnVeille) {

        this.type = type;
        this.designation = designation;
        this.dateCreation = dateCreation;
        this.pictures = pictures;
        this.miseEnVeille = miseEnVeille;
    }

    public Produit() {
    }


}
