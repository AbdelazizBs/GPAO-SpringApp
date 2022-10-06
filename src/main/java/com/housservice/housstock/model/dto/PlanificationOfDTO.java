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

//    public PlanificationOfDTO(String commentaire, List<String> idPersonnels, LocalDate duréeReelOperation, Date dateLancementReel, LocalDate heureDebutPrevue, LocalDate heureFinReel, String quantiteInitiale, String quantiteConforme, String quantiteNonConforme, LocalDate heureDebutReel, Date dateLancementPrevue, String idLigneCommandeClient, String idEtapeProductions, String idMachine, LocalDate heureFinPrevue) {
//        this.commentaire = commentaire;
//        this.idPersonnels = idPersonnels;
//        this.duréeReelOperation = duréeReelOperation;
//        this.dateLancementReel = dateLancementReel;
//        this.heureDebutPrevue = heureDebutPrevue;
//        this.heureFinReel = heureFinReel;
//        this.quantiteInitiale = quantiteInitiale;
//        this.quantiteConforme = quantiteConforme;
//        this.quantiteNonConforme = quantiteNonConforme;
//        this.heureDebutReel = heureDebutReel;
//        this.dateLancementPrevue = dateLancementPrevue;
//        this.idLigneCommandeClient = idLigneCommandeClient;
//        this.idEtapeProductions = idEtapeProductions;
//        this.idMachine = idMachine;
//        this.heureFinPrevue = heureFinPrevue;
//    }

    @Size(max = 100)
    private String commentaire;
    private List<String> idPersonnels;
    private LocalDate duréeReelOperation ;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateLancementReel ;
    private LocalDate heureDebutPrevue ;
    private LocalDate heureFinReel ;

    @Size(max = 100)
    private String quantiteInitiale;

    @Size(max = 100)
    private String quantiteConforme;

    @Size(max = 100)
    private String quantiteNonConforme;







    private LocalDate heureDebutReel ;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateLancementPrevue ;

    private String idLigneCommandeClient ;

    private String idEtapeProductions;

    private String idMachine;




    private LocalDate heureFinPrevue ;


    public PlanificationOfDTO() {

    }
}
