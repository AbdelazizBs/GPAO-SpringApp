package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter

public class CommandeDto {
    @Id
    private String id;
    @NotEmpty
    private String fournisseur;
    private Date dateCommande;
    private String numBcd;
    private boolean miseEnVeille;

    private String commentaire;

    private List<Article> article;
    private String etat;
    private String type;
    private boolean terminer;
    private int counter;

}
