package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
    private String type;
    private String nature;
    private List<String> parentsId;
    private List<String> childrensName;
    private List<Nomenclature> childrens;
    private List<EtapeProduction> etapeProductions;

    private String categorie;
    private String refIris;

    private Picture picture;

    private Date date;

    private boolean miseEnVeille;

    private Date dateMiseEnVeille;

}
