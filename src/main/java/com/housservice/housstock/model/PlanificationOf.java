package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
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


    private Date dateLancementReel;
    private LocalDateTime heureFinReel;
    private LocalDateTime heureDebutReel;

    private int quantiteInitiale;


    public PlanificationOf() {

    }
}
