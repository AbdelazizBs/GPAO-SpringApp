package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection="PlanEtapes")
public class PlanEtapes {
    @Id
    private String id;
    private String idOf;

    private String nomEtape;
    private String refMachine;
    private String monitrice;

    private List<String> personnels;
    private Date datePrevu;
    private Date dateReel;

    private Date heureDebutPrevue;
    private Date heureFinPrevue;
    private Date heureDebutReel;
    private Date heureFinReel;
    private int quantiteNonConforme;
    private int quantiteConforme;
    private int quantiteInitiale;
    private String commentaire;
    private String ref;
    private Boolean etat;

    private Boolean terminer;


    public PlanEtapes() {
    }

    public PlanEtapes(String id, String idOf, String nomEtape, String refMachine, String monitrice, List<String> personnels, Date datePrevu, Date dateReel, Date heureDebutPrevue, Date heureFinPrevue, Date heureDebutReel, Date heureFinReel, int quantiteNonConforme, int quantiteConforme, int quantiteInitiale, String commentaire, String ref, Boolean etat, Boolean terminer) {
        this.id = id;
        this.idOf = idOf;
        this.nomEtape = nomEtape;
        this.refMachine = refMachine;
        this.monitrice = monitrice;
        this.personnels = personnels;
        this.datePrevu = datePrevu;
        this.dateReel = dateReel;
        this.heureDebutPrevue = heureDebutPrevue;
        this.heureFinPrevue = heureFinPrevue;
        this.heureDebutReel = heureDebutReel;
        this.heureFinReel = heureFinReel;
        this.quantiteNonConforme = quantiteNonConforme;
        this.quantiteConforme = quantiteConforme;
        this.quantiteInitiale = quantiteInitiale;
        this.commentaire = commentaire;
        this.ref = ref;
        this.etat = etat;
        this.terminer = terminer;
    }
}
