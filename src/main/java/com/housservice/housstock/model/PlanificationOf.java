package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class PlanificationOf {

    @Transient
    public static final String SEQUENCE_NAME = "planificationOf_sequence";


    @Id
    private String id;

    private LigneCommandeClient ligneCommandeClient;

    private String nomEtape;

    private Machine machine;

    private List<Personnel> personnels;

    private LocalDate dateLancementPrevue;

    private LocalDate dateLancementReel;
    private LocalDate heureDebutPrevue;
    private LocalDate heureFinPrevue;
    private LocalDate heureFinReel;
    private LocalDate heureDebutReel;
    private LocalDate duréeReelOperation;

    @Size(max = 100)
    private String quantiteInitiale;

    @Size(max = 100)
    private String quantiteConforme;

    @Size(max = 100)
    private String quantiteNonConforme;


    @Size(max = 100)
    private String commentaire;

    public PlanificationOf() {

    }
}
