package com.housservice.housstock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@Getter
@Setter
 public class PlanificationOf {

   @Transient
   public static final String SEQUENCE_NAME = "planificationOf_sequence";



 @Id
   private String id;

    private  LigneCommandeClient ligneCommandeClient ;

    private EtapeProduction etapeProductions;

    private  Machine machine;

    private List<Personnel> personnels;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateLancementPrevue ;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateLancementReel ;
    private LocalDate heureDebutPrevue ;
    private LocalDate heureFinPrevue ;
    private LocalDate heureFinReel ;
    private LocalDate heureDebutReel ;
    private LocalDate duréeReelOperation ;


    @Size(max = 100)
    private String quantiteInitiale;

    @Size(max = 100)
    private String quantiteConforme;

    @Size(max = 100)
    private String quantiteNonConforme;

 public PlanificationOf(LigneCommandeClient ligneCommandeClient, EtapeProduction etapeProductions, Machine machine, List<Personnel> personnels, Date dateLancementPrevue, Date dateLancementReel, LocalDate heureDebutPrevue, LocalDate heureFinPrevue, LocalDate heureFinReel, LocalDate heureDebutReel, LocalDate duréeReelOperation, String quantiteInitiale, String quantiteConforme, String quantiteNonConforme, String commentaire) {
  this.ligneCommandeClient = ligneCommandeClient;
  this.etapeProductions = etapeProductions;
  this.machine = machine;
  this.personnels = personnels;
  this.dateLancementPrevue = dateLancementPrevue;
  this.dateLancementReel = dateLancementReel;
  this.heureDebutPrevue = heureDebutPrevue;
  this.heureFinPrevue = heureFinPrevue;
  this.heureFinReel = heureFinReel;
  this.heureDebutReel = heureDebutReel;
  this.duréeReelOperation = duréeReelOperation;
  this.quantiteInitiale = quantiteInitiale;
  this.quantiteConforme = quantiteConforme;
  this.quantiteNonConforme = quantiteNonConforme;
  this.commentaire = commentaire;
 }

 @Size(max = 100)
    private String commentaire;

}
