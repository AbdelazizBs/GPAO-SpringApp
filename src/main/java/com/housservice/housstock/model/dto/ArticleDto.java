package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter


public class ArticleDto {
    @Id
    private String id;

    @Size(min = 2, message = "Article designationMatiere should have at least 2 characters")
    @NotEmpty(message = "Article designationMatiere  should not empty")
    private String designationMatiere;

    @Size(min = 2, message = "Article dateLivraison should have at least 2 characters")
    @NotEmpty(message = "Article dateLivraisoni should not empty")
    private Date dateLivraison;
    @Size(min = 2, message = "Article commentaire should have at least 2 characters")
    @NotEmpty(message = "Article commentaire should not empty")
    private String commentaire;
    @Size(min = 2, message = "Article prixUnitaire should have at least 2 characters")
    @NotEmpty(message = "Article prixUnitaire should not empty")
    private float prixUnitaire ;
    @Size(min = 2, message = "Article quantite should have at least 2 characters")
    @NotEmpty(message = "Article quantite should not empty")
    private int quantite;
    @Size(min = 2, message = "Article prix should have at least 2 characters")
    @NotEmpty(message = "Article prix should not empty")
    private float prix;
}
