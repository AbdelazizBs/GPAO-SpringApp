package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.Machine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private List<String> machinesId;
    private List<String> nomPersonnels;

    private Date dateLancementReel;

    private Date heureFinReel;
    @NotNull(message = "{http.error.0001}")
    @NotEmpty(message = "{http.error.0001}")
    private int quantiteInitiale;
    private int qteConforme;
    private int qteNonConforme;

    private Date heureDebutReel;

    private LigneCommandeClient ligneCommandeClient;

    private String nomEtape;
    private String operationType;
    private String nomNomenclature;
    @NotNull(message = "{http.error.0001}")

    private Date dateLancementPrevue;
    @NotNull(message = "{http.error.0001}")

    private Date heureFinPrevue;
    @NotNull(message = "{http.error.0001}")

    private Date heureDebutPrevue;
    private List<String> libelleMachine;
    private String commentaire;
    private boolean lanced;


    public PlanificationOfDTO() {

    }
}
