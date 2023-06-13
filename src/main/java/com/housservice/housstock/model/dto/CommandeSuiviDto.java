package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CommandeSuiviDto {

    @Id
    private String id;
    private String raisonSocial;
    @NotNull
    private int rate;
    private String commentaire;
    @NotNull(message = " Verifier Commande date Liv")
    private String date;
    private String numBcd;

    @NotEmpty
    private String vprix;

    @NotEmpty
    private String vdate;

}
