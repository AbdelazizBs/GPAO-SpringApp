package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.PrixAchat;
import com.housservice.housstock.model.PrixVente;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class AffectationProduitDto {
    @Id
    private String id;
    private String refClient;
    private int minimunVente;
    private String uniteVente;
    private Date dateEffet;
    private String devise;
    private float prix;
    private List<PrixVente> prixVente;
    private String idProduit;
    private String destination;

    private String listClient;
}
