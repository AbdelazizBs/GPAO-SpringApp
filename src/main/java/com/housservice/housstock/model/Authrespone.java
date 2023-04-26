package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "token")
public class Authrespone {

    private final String jwt;

    public Authrespone(String jwt) {
        this.jwt = jwt;
    }
}
