package com.housservice.housstock.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;

/**
 *
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class ContactDto {

    @Id
    private String id;
    @Size(max = 100)
    private String nom;


    @Size(max = 100)
    private String fonction;


    @Size(max = 100)
    private String address;


    @Size(max = 100)
    private String phone;


    @Size(max = 100)
    private String email;


    @Size(max = 100)
    private String mobile;


}
