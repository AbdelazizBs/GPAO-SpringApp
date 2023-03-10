package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "Commande")

public class Commande {
    @Transient
    public static final String SEQUENCE_NAME ="Commande_sequence";

    @Id
    private String id;
    private Date dateCommande;
    private String Commentaire;
    private String numBcd;
    private String Fournisseur;

    public Commande(Date dateCommande, String commentaire, String numBcd, String fournisseur) {

this.dateCommande=dateCommande;
        Commentaire = commentaire;
        this.numBcd = numBcd;
        Fournisseur = fournisseur;
    }
    public Commande(){}
}
