package com.housservice.housstock.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EtatMachineDto {


    @Id
    private String id;

    private List<String> nomEtat;
    private String lastEtat;


    private String idMachine;

    private List<LocalDateTime> dateDebut;


    private List<LocalDateTime> dateFin;


}
