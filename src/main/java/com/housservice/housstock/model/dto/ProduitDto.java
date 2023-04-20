package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Getter
@Setter
public class ProduitDto {

    @Id
    private String id;
    private String ref;
    private String type;
    private String designation;
    private Date dateCreation;
}
