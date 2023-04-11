package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@Document(collection = "CommandeClientSuivi")
public class CommandeClientSuivi {

    @Id
    private String id;
    private String raisonSocial;
    private int rate;
    private String commentaire;
    private String date;

    public CommandeClientSuivi(String raisonSocial, int rate, String commentaire, String date) {
        this.raisonSocial = raisonSocial;
        this.rate = rate;
        this.commentaire = commentaire;
        this.date = date;
    }

    public CommandeClientSuivi() {
    }
}
