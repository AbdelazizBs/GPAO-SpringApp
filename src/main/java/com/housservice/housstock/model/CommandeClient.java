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
    private String Commentaire;
    private String numBcd;
    private String Client;
    private List<Article> article;

    public CommandeClient(String id, String dateCommande, String commentaire, String numBcd, String client, List<Article> article) {
        this.id = id;
        this.dateCommande = dateCommande;
        Commentaire = commentaire;
        this.numBcd = numBcd;
        Client = client;
        this.article = article;
    }

    public CommandeClient(String dateCommande, String commentaire, String numBcd, String client) {

        this.dateCommande=dateCommande;
        Commentaire = commentaire;
        this.numBcd = numBcd;
        Client = client;
    }
    public CommandeClient(){}
}


