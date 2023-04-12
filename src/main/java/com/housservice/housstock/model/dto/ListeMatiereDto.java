package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListeMatiereDto {
    @Id
    private String id;
    @NotNull
    private String designation;
    private String typePapier;
    private float grammage;
    private String couleur;
    private float largeur;
    private float longueur;
    private int minimumStock;
    private String uniteConsommation;
    private String typePvc;
    private String typeVernis;
    private String typeEncre;
    private String embout;
    private float diametre;
    private String typeCordon;
    private String laize;
    private String micron;
    private String typePelliculage;
    private String type;
}
