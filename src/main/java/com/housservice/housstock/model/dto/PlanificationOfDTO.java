package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Machine;
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

    private Date heureFinReel;

    private int quantiteInitiale;


    private Date heureDebutReel;

    private LigneCommandeClient ligneCommandeClient;

    private String nomEtape;
    private String nomNomenclature;

    private List<Machine> machines;


    public PlanificationOfDTO() {

    }
}
