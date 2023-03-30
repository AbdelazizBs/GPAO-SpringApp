package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Document(collection = "Compte")
public class Compte {
    @Id
    private String id;
    private String email;

    private String password;
    private String idPersonnel;
    private String role;
    private Date datelastlogin;

    public Compte(String email, String password, String idPersonnel, String role, Date datelastlogin) {
        this.email = email;
        this.password = password;
        this.idPersonnel = idPersonnel;
        this.role = role;
        this.datelastlogin = datelastlogin;
    }

    public Compte() {
    }
}