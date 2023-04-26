package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection = "Commande Client")

public class CommandeClient {
    @Transient
    public static final String SEQUENCE_NAME ="CommandeClient_sequence";

    @Id
    private String id;
    private String dateCommande;
    private String commentaire;
    private String numBcd;
    private String client;
    private List<Article> article;
    private boolean miseEnVeille;

    public CommandeClient(String id, String dateCommande, String commentaire, String numBcd, String client, List<Article> article,boolean miseEnVeille) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.commentaire = commentaire;
        this.numBcd = numBcd;
        this.client = client;
        this.article = article;
        this.miseEnVeille=miseEnVeille;
    }

    public CommandeClient(String dateCommande, String commentaire, String numBcd, String client, boolean miseEnVeille) {
        this.dateCommande = dateCommande;
        this.commentaire = commentaire;
        this.numBcd = numBcd;
        this.client = client;
        this.miseEnVeille = miseEnVeille;
    }

    public CommandeClient(){}
}


