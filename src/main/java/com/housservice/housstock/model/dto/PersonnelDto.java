package com.housservice.housstock.model.dto;


import com.housservice.housstock.model.Comptes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class PersonnelDto {

    private String id;


    @NotEmpty(message = "personnel name should not empty")
    @Size(min = 2, message = "personnel name should have at least 2 characters")
    private String nom;

    @Size(min = 2, message = "personnel name should have at least 2 characters")
    @NotEmpty(message = "personnel prenom should not empty")
    private String prenom;


    @Size(min = 2, message = "personnel adresse should have at least 2 characters")
    @NotEmpty(message = "personnel adresse should not empty")
    private String adresse;


    private String photo;

    private String ville;


    private String codePostal;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String cin;

    private String email;


    private String sexe;


    private String rib;


    private String poste;

    @NotNull(message = "personnel date embauche should not empty")
    private Date dateEmbauche;


    @NotNull(message = "personnel date Naissance should not empty")
    private Date dateNaissance;


    private String echelon;


    private String numCnss;


    private String situationFamiliale;


    private String nbrEnfant;


    private String typeContrat;


    @NotEmpty(message = "personnel matricule should not empty")
    private String matricule;


    private String phone;

    private String categorie;

    private Comptes compte = new Comptes();


    private boolean miseEnVeille;


}
