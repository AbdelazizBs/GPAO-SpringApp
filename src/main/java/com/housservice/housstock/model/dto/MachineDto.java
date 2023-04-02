package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class MachineDto {
    @Id
    private String id;
    @Size(min = 2, message = "machine reference should have at least 2 characters")
    @NotEmpty(message = "machine reference  should not empty")
    private String refMachine;
    private Date dateMaintenance ;
    private boolean miseEnVeille;
    private String type;
    @Size(min = 2, message = "machine libelle should have at least 2 characters")
    @NotEmpty(message = "machine libelle  should not empty")
    private  String liebelle ;
    private int nbConducteur;
    @Size(min = 2, message = "nom conducteur should have at least 2 characters")
    @NotEmpty(message = "nom conducteur should not empty")
    private String nomConducteur;

}
