package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "Article")
public class Article {

    @Transient
    public static final String SEQUENCE_NAME ="article_sequence";


    @Id
    private String id;
    private String designationMatiere;
    private Date dateLivraison;
    private String commentaire;
    private String prixUnitaire;
    private int quantite;
    private int prix;

    public Article() {
    }

    public Article(String designationMatiere, Date dateLivraison, String commentaire, String prixUnitaire, int quantite, int prix) {
        this.designationMatiere = designationMatiere;
        this.dateLivraison = dateLivraison;
        this.commentaire = commentaire;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
