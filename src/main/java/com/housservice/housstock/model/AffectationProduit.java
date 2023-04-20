package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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
    /*private Date dateEffet;
    private String devise;
    private float prix;*/
    private List<PrixVente> prixVente;
    private String idProduit;
    private String idPrixVente;
}
