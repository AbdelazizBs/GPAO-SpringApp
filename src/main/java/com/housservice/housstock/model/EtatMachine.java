package com.housservice.housstock.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection="EtatMachine")
public class EtatMachine {


    @Transient
    public static final String SEQUENCE_NAME ="etat_sequence";

    @Id
    private String id;

    private String nomEtat;


    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private LocalDate dateDebut;

    private String idMachine;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private LocalDate dateFin;

    public EtatMachine(String nomEtat, LocalDate dateDebut, LocalDate dateFin,String idMachine) {
        this.nomEtat=nomEtat;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.idMachine=idMachine;
    }
}
