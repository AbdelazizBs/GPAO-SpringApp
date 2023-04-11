package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter public class CommandeClientSuiviDto {
    @Id
    private String id;
    private String raisonSocial;
    private int rate;
    private String commentaire;
    private String date;
}
