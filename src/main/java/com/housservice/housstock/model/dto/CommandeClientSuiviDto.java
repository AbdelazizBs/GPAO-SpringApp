package com.housservice.housstock.model.dto;

import org.springframework.data.annotation.Id;

public class CommandeClientSuiviDto {
    @Id
    private String id;
    private String raisonSocial;
    private int rate;
    private String commentaire;
    private String date;
}
