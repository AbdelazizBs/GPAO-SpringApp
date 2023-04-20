package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Document(collection="Prix Vente")
public class PrixVente {
    @Id
    private String id;
    private float prix;
    private Date dateEffet;
    private String devise;

    public PrixVente() {
    }

    public PrixVente(float prix, Date dateEffet, String devise) {
        this.prix = prix;
        this.dateEffet = dateEffet;
        this.devise = devise;
    }
}
