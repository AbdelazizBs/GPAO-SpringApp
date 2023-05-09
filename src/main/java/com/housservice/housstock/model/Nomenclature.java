package com.housservice.housstock.model;

import com.housservice.housstock.model.enums.TypeNomEnClature;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Document(collection = "Nomenclature")
public class Nomenclature {

    @Transient
    public static final String SEQUENCE_NAME = "nomenclature_sequence";

    @Id
    private String id;


    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String nomNomenclature;

    @NotBlank
    @Size(max = 100)
    private String description;
    @NotBlank
    @Size(max = 100)
    private String refIris;

    private TypeNomEnClature type;

    @NotBlank
    @Size(max = 100)
    private String nature;

    @NotBlank
    @Size(max = 100)
    private String categorie;
    private List<String> clientId = new ArrayList<>();
    private List<String> fournisseurId = new ArrayList<>();

    private List<String> childrensId;
    private List<String> parentsName = new ArrayList<>();
    private List<String> childrensName = new ArrayList<>();

    private List<String> parentsId;

    private List<Nomenclature> childrens;


    private Picture picture;
    private double price;
    private int quantityMin;
    private int quantityMax;
    private int quantity;
    private int quantityStock;


    private Date date;

    private boolean miseEnVeille;

    private Date dateMiseEnVeille;
    private List<EtapeProduction> etapeProductions;


    public Nomenclature() {

    }

    public Nomenclature(String id, @NotBlank @Size(max = 100) String nomNomenclature,
                        @NotBlank @Size(max = 100) String description, TypeNomEnClature type,
                        @NotBlank @Size(max = 100) String nature, @NotBlank @Size(max = 100) String categorie,
                        Date date, boolean miseEnVeille, Date dateMiseEnVeille) {
        super();
        this.id = id;
        this.nomNomenclature = nomNomenclature;
        this.description = description;
        this.type = type;
        this.nature = nature;
        this.categorie = categorie;
        this.date = date;
        this.miseEnVeille = miseEnVeille;
        this.dateMiseEnVeille = dateMiseEnVeille;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nomenclature nomenclature = (Nomenclature) o;
        return id.equals(nomenclature.id);
    }


}
