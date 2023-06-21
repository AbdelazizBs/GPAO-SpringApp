package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.PrixAchat;
import com.housservice.housstock.model.PrixVente;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class AffectationProduitDto {
    @Id
    private String id;
    @NotEmpty
    private String refClient;
    @NotNull
    private int minimunVente;
    @NotEmpty
    private String uniteVente;
    @NotNull
    private Date dateEffet;
    @NotEmpty
    private String devise;
    @NotNull
    private float prix;
    private List<PrixVente> prixVente;
    private String idProduit;
    private String destination;

    private String listClient;
}
