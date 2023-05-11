package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection="PlanEtapes")
public class PlanEtapes {
    @Id
    private String id;
    private String nomEtape;
    private String refMachine;
    private List<String> personnels;
    private Date datePrevu;
    private Date heureDebutPrevue;
    private Date heureFinPrevue;
    private Date heureDebutReel;
    private Date heureFinReel;

    public PlanEtapes() {
    }

    public PlanEtapes(String nomEtape, String refMachine, List<String> personnels, Date datePrevu, Date heureDebutPrevue, Date heureFinPrevue, Date heureDebutReel, Date heureFinReel) {
        this.nomEtape = nomEtape;
        this.refMachine = refMachine;
        this.personnels = personnels;
        this.datePrevu = datePrevu;
        this.heureDebutPrevue = heureDebutPrevue;
        this.heureFinPrevue = heureFinPrevue;
        this.heureDebutReel = heureDebutReel;
        this.heureFinReel = heureFinReel;
    }
}
