package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Liste Matiere")

public class ListeMatiere {
    @Transient
    public static final String SEQUENCE_NAME ="listematiere_sequence";
    @Id
   private String id;
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


    public ListeMatiere( String designation, String type,String typePapier, float grammage, String couleur, float largeur, float longueur, int minimumStock, String uniteConsommation, String typePvc, String typeVernis, String typeEncre, String embout, float diametre, String typeCordon, String laize, String micron, String typePelliculage) {

        this.designation = designation;
        this.type=type;
        this.typePapier = typePapier;
        this.grammage = grammage;
        this.couleur = couleur;
        this.largeur = largeur;
        this.longueur = longueur;
        this.minimumStock = minimumStock;
        this.uniteConsommation = uniteConsommation;
        this.typePvc = typePvc;
        this.typeVernis = typeVernis;
        this.typeEncre = typeEncre;
        this.embout = embout;
        this.diametre = diametre;
        this.typeCordon = typeCordon;
        this.laize = laize;
        this.micron = micron;
        this.typePelliculage = typePelliculage;
    }

    public ListeMatiere() {
    }
}
