package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class CommandeSuiviDto {

    @Id
    private String id;
    private String raisonSocial;
    private int rate;
    private String commentaire;
    private String date;
}
