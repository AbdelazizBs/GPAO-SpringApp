package com.housservice.housstock.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.housservice.housstock.model.Produit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class ArticleDto {
    @Id
    private String id;

    @NotEmpty(message = "Article designationMatiere  should not empty")
    private String designationMatiere;

    private Produit produit;

    @NotNull(message = "personnel date embauche should not empty")
    private Date dateLivraison;
    private String prixUnitaire;
    @NotNull
    private int quantite;
    @NotNull
    private int prix;

    private String commentaire;
}
