package com.housservice.housstock.model.dto;

import com.housservice.housstock.model.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

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

}
