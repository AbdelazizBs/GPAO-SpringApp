package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Personnel;
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
    private Date heureDebutPrevue;
    private Date heureDebutReel;
    private Date heureFinPrevue;
    private Date heureFinReel;
    private int quantiteNonConforme;
    private int quantiteConforme;
    private int quantiteInitiale;
    private String commentaire;
    private Boolean etat;

}
