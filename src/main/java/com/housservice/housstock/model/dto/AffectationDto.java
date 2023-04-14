package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.PrixAchat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
@Getter
@Setter
public class AffectationDto {
    @Id
    private String id;
    private String refFournisseur;
    private int minimunAchat;
    private String uniteAchat;
    private List<PrixAchat> prixAchat;
    private String idMatiere;
    private String idPrixAchat;
}
