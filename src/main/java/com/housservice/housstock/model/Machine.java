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
@Document(collection = "Machine")
public class Machine {

    @Transient
    public static final String SEQUENCE_NAME = "machine_sequence";

    @Id
    private String id;


    private String reference;


    private String libelle;

    private EtatMachine etatMachine;


    private int nbrConducteur;


    private Date dateMaintenance;
    private Boolean enVeille;


    private EtapeProduction etapeProduction;

    private List<Personnel> personnel;

}
