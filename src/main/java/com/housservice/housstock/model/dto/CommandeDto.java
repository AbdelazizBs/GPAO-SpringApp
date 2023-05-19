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
    @Size(min = 2, message = "fournisseur numBcd should have at least 2 characters")
    @NotEmpty(message = "fournisseur numBcd  should not empty")
    private String numBcd;
    private boolean miseEnVeille;

    private String commentaire;

    private List<Article> article;
    private String etat;
    private String type;
    private boolean terminer;


}
