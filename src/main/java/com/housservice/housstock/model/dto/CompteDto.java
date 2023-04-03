package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Getter
@Setter
public class CompteDto {
    @Id
    private String id;
    private String email;
    private String password;
    private String idPersonnel;
    private String role;
    private Date datelastlogin;
    private boolean miseEnVeille;
}
