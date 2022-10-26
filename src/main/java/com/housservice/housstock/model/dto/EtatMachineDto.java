package com.housservice.housstock.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
public class EtatMachineDto {



    @Id
    private String id;

    private String nomEtat;

private String idMachine ;

    private LocalDate dateDebut;


    private LocalDate dateFin;


}
