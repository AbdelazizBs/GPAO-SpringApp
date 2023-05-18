package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Getter
@Setter
public class PrixVenteDto {
    @Id
    private String id;
    private float prix;
    private Date dateEffet;
    private String devise;
    private int minimunVente;
    private String uniteVente;
}
