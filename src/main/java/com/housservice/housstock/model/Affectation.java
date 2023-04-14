package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection="Affectation Matiere Fournisseur")
public class Affectation {
    @Transient
    public static final String SEQUENCE_NAME ="affectation_sequence";
    @Id
    private String id;
    private String refFournisseur;
    private int minimunAchat;
    private String uniteAchat;
    /*private Date dateEffet;
    private String devise;
    private float prix;*/
    private List<PrixAchat> prixAchat;
    private String idMatiere;
    private String idPrixAchat;



}
