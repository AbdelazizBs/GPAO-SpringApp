package com.housservice.housstock.model.dto;


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
public class EtatMachineDto {



    @Id
    private String id;

    private String nomEtat;

private String idMachine ;

    private LocalDate dateDebut;


    private LocalDate dateFin;


}
