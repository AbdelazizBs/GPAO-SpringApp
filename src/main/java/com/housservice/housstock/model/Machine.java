package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sun.util.calendar.BaseCalendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "machine")
public class Machine {
    @Id
   private  String id;
    private String refMachine;
    private List<String> nomConducteur;
    private String libelle;
    private int nbConducteur;
    private Date dateMaintenance;
    private String type;
    private boolean miseEnVeille;
    private String etat;
    private int counter;
    public Machine(String refMachine, List<String> nomConducteur, String libelle, int nbConducteur, Date dateMaintenance, String type, boolean miseEnVeille, String etat) {
        this.refMachine = refMachine;
        this.nomConducteur = nomConducteur;
        this.libelle = libelle;
        this.nbConducteur = nbConducteur;
        this.dateMaintenance = dateMaintenance;
        this.type = type;
        this.miseEnVeille = miseEnVeille;
        this.etat = etat;
    }

    public Machine() {
    }
}
