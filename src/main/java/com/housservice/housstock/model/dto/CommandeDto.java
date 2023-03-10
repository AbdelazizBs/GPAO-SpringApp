package com.housservice.housstock.model.dto;

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
    private String Fournisseur;
    @Size(min = 2, message = "fournisseur raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "fournisseur raisonSocial(intitule)  should not empty")
    private String numBcd;

    private String dateCommande;
    private String Commentaire;




}
