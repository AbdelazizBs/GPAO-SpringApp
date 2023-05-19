package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection="Prix Achat")
public class PrixAchat {
    @Id
    private String id;
    private int prix;
    private Date dateEffet;
    private String devise;
    private int minimumachat;
    private String uniteAchat;

    public PrixAchat(int prix, Date dateEffet, String devise, int minimumachat, String uniteAchat) {
        this.prix = prix;
        this.dateEffet = dateEffet;
        this.devise = devise;
        this.minimumachat = minimumachat;
        this.uniteAchat = uniteAchat;
    }

    public PrixAchat() {
    }
}
