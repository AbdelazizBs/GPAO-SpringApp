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
    private Date dateCommande;
    private String numBcd;
    private boolean miseEnVeille;
    private String commentaire;
    private List<Article> article;
    private String etat;
    private String type;
    private boolean terminer;


    public Commande(String fournisseur, Date dateCommande, String numBcd, boolean miseEnVeille, String commentaire, List<Article> article, String etat, String type, boolean terminer) {
        this.fournisseur = fournisseur;
        this.dateCommande = dateCommande;
        this.numBcd = numBcd;
        this.miseEnVeille = miseEnVeille;
        this.commentaire = commentaire;
        this.article = article;
        this.etat = etat;
        this.type = type;
        this.terminer = terminer;
    }

    public Commande(){}
}
