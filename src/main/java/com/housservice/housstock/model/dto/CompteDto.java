package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class CompteDto {
    @Indexed(unique=true)
    private String email;
    private String password;
    private String post;
}
