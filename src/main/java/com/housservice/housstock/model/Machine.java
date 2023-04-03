package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "machine")
public class Machine {
    @Id
   private  String id;
    private String refMachine;
    private String nomConducteur;
    private String libelle;
    private int nbConducteur;
    private Date dateMaintenance;
    private String type;
    private boolean miseEnVeille;

    public Machine( String refMachine,String nomConducteur, String libelle, int nbConducteur, Date dateMaintenance, String type, boolean miseEnVeille) {

        this.refMachine= refMachine;
        this.libelle = libelle;
        this.nbConducteur = nbConducteur;
        this.dateMaintenance = dateMaintenance;
        this.type = type;
        this.miseEnVeille = miseEnVeille;
        this.nomConducteur=nomConducteur;
    }

    public Machine() {
    }
}
