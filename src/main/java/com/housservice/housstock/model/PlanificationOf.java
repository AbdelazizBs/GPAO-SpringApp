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

    private LocalDate dateLancementPrevue ;

    private LocalDate dateLancementReel ;
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



 @Size(max = 100)
    private String commentaire;

 public PlanificationOf() {

 }

 public PlanificationOf(LigneCommandeClient ligneCommandeClient, EtapeProduction etapeProduction, Machine machine, List<Personnel> personnels, Date date, Date date1, LocalDate max_date, LocalDate max_date1, LocalDate max_date2, LocalDate max_date3, LocalDate max_date4, String s, String s1, String s2, String s3) {
 }
}
