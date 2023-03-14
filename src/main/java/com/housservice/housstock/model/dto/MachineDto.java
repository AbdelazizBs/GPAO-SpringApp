package com.housservice.housstock.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * @author houssem.khadraoui@gmail.com
 */
@Getter
@Setter
public class MachineDto {

    @Transient
    public static final String SEQUENCE_NAME = "machine_sequence";

    @Id
    private String id;

    private String reference;

    private String libelle;

    private int nbrConducteur;
    private List<String> nomConducteurs;

    private Date dateMaintenance;

    private String nomEtatMachine;
    private Boolean enVeille;
    private String nomEtapeProduction;


}
