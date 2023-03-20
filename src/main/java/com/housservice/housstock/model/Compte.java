package com.housservice.housstock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document(collection = "comptes")
public class Compte {
    @Indexed(unique=true)
    private String email;
    private String password;
    private String post;

    public Compte(String email, String password, String post) {
        this.email = email;
        this.password = password;
        this.post = post;
    }

    public Compte() {
    }
}