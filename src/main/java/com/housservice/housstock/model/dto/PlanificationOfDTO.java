package com.housservice.housstock.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class PlanificationOfDTO {

    @Id
    private String id ;
    @Size(max = 100)
    private String commentaire;
    private List<String> idPersonnels;
    private LocalDate duréeReelOperation ;

    private LocalDate dateLancementReel ;
    private LocalDate heureDebutPrevue ;
    private LocalDate heureFinReel ;

    @Size(max = 100)
    private String quantiteInitiale;

    @Size(max = 100)
    private String quantiteConforme;

    @Size(max = 100)
    private String quantiteNonConforme;

    private LocalDate heureDebutReel ;
    private LocalDate dateLancementPrevue ;

    private String idLigneCommandeClient ;

    private String nomEtape;

    private String idMachine;




    private LocalDate heureFinPrevue ;


    public PlanificationOfDTO() {

    }
}
