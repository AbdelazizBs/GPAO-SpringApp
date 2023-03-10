package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "Commande")

public class Commande {
    @Transient
    public static final String SEQUENCE_NAME ="Commande_sequence";

    @Id
    private String id;
    private String fournisseur;
    private String numBcd;
    private String dateCommande;
    private String commentaire;


    public Commande(String fournisseur, String numBcd,  String commentaire,String dateCommande) {
        this.fournisseur = fournisseur;
        this.numBcd = numBcd;
        this.dateCommande = dateCommande;
        this.commentaire = commentaire;
    }

    public Commande(){}
}
