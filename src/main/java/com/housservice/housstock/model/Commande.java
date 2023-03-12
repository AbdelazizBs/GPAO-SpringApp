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
    private Date dateCommande;
    private String Commentaire;
    private String numBcd;
    private String Fournisseur;
    private List<Article> article;

    public Commande(String id, Date dateCommande, String commentaire, String numBcd, String fournisseur, List<Article> article) {
        this.id = id;
        this.dateCommande = dateCommande;
        Commentaire = commentaire;
        this.numBcd = numBcd;
        Fournisseur = fournisseur;
        this.article = article;
    }

    public Commande(Date dateCommande, String commentaire, String numBcd, String fournisseur) {

this.dateCommande=dateCommande;
        Commentaire = commentaire;
        this.numBcd = numBcd;
        Fournisseur = fournisseur;
    }
    public Commande(){}
}
