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
@Document(collection="Affectation produit Client")
public class AffectationProduit {
    @Transient
    public static final String SEQUENCE_NAME ="affectationClient_sequence";
    @Id
    private String id;
    private String refClient;
    private int minimunVente;
    private String uniteVente;
    private Date dateEffet;
    private String devise;
    private float prix;
    private List<PrixVente> prixVente;
    private String idProduit;
    private String destination;

    private String listClient;

    public AffectationProduit(String refClient, int minimunVente, String uniteVente, Date dateEffet, String devise, float prix, List<PrixVente> prixVente, String idProduit, String destination, String listClient) {
        this.refClient = refClient;
        this.minimunVente = minimunVente;
        this.uniteVente = uniteVente;
        this.dateEffet = dateEffet;
        this.devise = devise;
        this.prix = prix;
        this.prixVente = prixVente;
        this.idProduit = idProduit;
        this.destination = destination;
        this.listClient = listClient;
    }
}
