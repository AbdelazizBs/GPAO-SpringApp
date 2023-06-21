package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "CommandeSuivi")
public class CommandeSuivi {

    @Id
    private String id;
    private String raisonSocial;
    private int rate;
    private String commentaire;
    private Date date;
    private String numBcd;
    private String vprix;

    private String vdate;

    public CommandeSuivi(String id, String raisonSocial, int rate, String commentaire, Date date, String numBcd, String vprix, String vdate) {
        this.id = id;
        this.raisonSocial = raisonSocial;
        this.rate = rate;
        this.commentaire = commentaire;
        this.date = date;
        this.numBcd = numBcd;
        this.vprix = vprix;
        this.vdate = vdate;
    }

    public CommandeSuivi() {
    }
}
