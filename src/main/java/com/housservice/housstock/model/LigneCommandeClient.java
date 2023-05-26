package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Document(collection = "LigneCommandeClient")
public class LigneCommandeClient {
    @Transient
    public static final String SEQUENCE_NAME = "ligneCommandeClient_sequence";

    @Id
    private String id;

    @NotBlank
    private String quantite;

    private int progress;


    private Nomenclature nomenclature;

    private String idCommandeClient;

    private String numCmdClient;


    private Date delai;
    private boolean lanced;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommandeClient ligneCommandeClient = (LigneCommandeClient) o;
        return id.equals(ligneCommandeClient.id);
    }

}
