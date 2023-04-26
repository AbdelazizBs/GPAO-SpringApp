package com.housservice.housstock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Getter
@Setter
public class PrixAchatDto {
    @Id
    private String id;
    private int prix;
    private Date dateEffet;
    private String devise;
}
