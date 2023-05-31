package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.PlanEtapes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlannificationDto {
    @Id
    private String id;
    private String refMachine;
    private List<String> personnels;
    private String nomEtape;
    private Article ligneCommandeClient;
    private int quantiteNonConforme;
    private int quantiteConforme;
    private int quantiteInitiale;
    private String commentaire;
    private Boolean plan;
    private Boolean etat;
    private List<PlanEtapes> etapes;
    private String ref;

    private int progress;
    private String idComm;
    private int counter;
}
