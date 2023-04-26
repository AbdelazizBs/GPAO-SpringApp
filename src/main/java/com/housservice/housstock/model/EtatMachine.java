package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "EtatMachine")
public class EtatMachine {


    @Transient
    public static final String SEQUENCE_NAME = "etat_sequence";

    @Id
    private String id;

    private List<String> nomEtat;
    private String lastEtat;


    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private List<LocalDateTime> dateDebut;

    private String idMachine;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private List<LocalDateTime> dateFin;


    public EtatMachine() {

    }
}
