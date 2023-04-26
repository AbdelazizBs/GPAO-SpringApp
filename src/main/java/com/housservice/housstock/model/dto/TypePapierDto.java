package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
public class TypePapierDto {
    @Id
    private  String id;
    private String nom;
}
