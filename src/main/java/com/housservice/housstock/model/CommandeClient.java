package com.housservice.housstock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "CommandeClient")
public class CommandeClient {

    @Transient
    public static final String SEQUENCE_NAME = "commandeClient_sequence";

    @Id
    private String id;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String typeCmd;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String numCmd;


    private boolean closed = false;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCmd;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCreationCmd;

    private Client client;

    private Boolean haveLc;

    private String etatProduction;
    private List<LigneCommandeClient> ligneCommandeClient;

    public CommandeClient(String typeCmd, String numCmd, Date dateCmd, Date dateCreationCmd, Boolean haveLc, String etatProduction, Client client) {
        this.typeCmd = typeCmd;
        this.numCmd = numCmd;
        this.dateCmd = dateCmd;
        this.dateCreationCmd = dateCreationCmd;
        this.haveLc = haveLc;
        this.etatProduction = etatProduction;
        this.client = client;

    }

    public CommandeClient() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandeClient commandeClient = (CommandeClient) o;
        return id.equals(commandeClient.id);
    }
}
