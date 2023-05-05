package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlanificationOfDTO {

    @Id
    private String id;

    private List<String> idPersonnels;

    private Date dateLancementReel;

    private LocalDateTime heureFinReel;

    @Size(max = 100)
    private int quantiteInitiale;


    private LocalDateTime heureDebutReel;

    private String idLigneCommandeClient;

    private String nomEtape;

    private String refMachine;


    public PlanificationOfDTO() {

    }
}
