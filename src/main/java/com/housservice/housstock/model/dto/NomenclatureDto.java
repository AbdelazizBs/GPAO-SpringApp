package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.enums.TypeNomEnClature;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NomenclatureDto {

    @Id
    private String id;
    private String nomNomenclature;
    private List<String> clientId;
    private List<String> fournisseurId;
    private String description;
    private TypeNomEnClature type;
    private String nature;
    private List<String> childrensId;
    private List<String> parentsName;
    private List<Nomenclature> childrens;
    private List<String> childrensName;
    private List<String> parentsId;


    private double price;
    private int quantityMin;
    private int quantityMax;
    private int quantity;
    private int quantityStock;
    private LocalDateTime durationOfFabrication;
    private List<EtapeProduction> etapeProductions;


    private String categorie;
    private String refIris;

    private Picture picture;

    private Date date;

    private boolean miseEnVeille;

    private Date dateMiseEnVeille;

}
