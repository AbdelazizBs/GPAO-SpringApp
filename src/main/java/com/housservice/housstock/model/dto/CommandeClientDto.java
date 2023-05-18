package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Article;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
public class CommandeClientDto {
    @Id
    private String id;
    private String dateCommande ;
    private String commentaire;
    private boolean miseEnVeille;
    private String etat;
    private String type;
    private boolean terminer;

    @Size(min = 2, message = "fournisseur raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "fournisseur raisonSocial(intitule)  should not empty")
    private String numBcd;
    private String client;
    private List<Article> article;
}
