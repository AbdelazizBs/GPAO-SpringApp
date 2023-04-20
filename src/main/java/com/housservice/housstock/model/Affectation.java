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
@Document(collection = "Affectation")
public class Affectation {

    @Transient
    public static final String SEQUENCE_NAME ="affectation_sequence";


    @Id
    private String id;

    private Date dateeffect;
    private String ref;
    private String devises;
    private int prix;
    private int minimumachat;
    private String unite;
    private String uniteAchat;

    private String listFournisseur;
    private String idmatiere;
    private String destination;

    private List<PrixAchat> prixAchat;


    public Affectation(Date dateeffect,String destination,String uniteAchat, String ref, String devises, int prix, int minimumachat, String unite, String listFournisseur, String idmatiere, List<PrixAchat> prixAchat) {
        this.dateeffect = dateeffect;
        this.ref = ref;
        this.devises = devises;
        this.prix = prix;
        this.minimumachat = minimumachat;
        this.uniteAchat = uniteAchat;
        this.destination=destination;
        this.unite = unite;
        this.listFournisseur = listFournisseur;
        this.idmatiere = idmatiere;
        this.prixAchat = prixAchat;
    }

    public Affectation() {
    }
}
