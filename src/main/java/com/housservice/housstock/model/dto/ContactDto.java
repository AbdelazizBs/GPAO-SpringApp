package com.housservice.housstock.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @Size(min = 2, message = "client raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "client raisonSocial(intitule)  should not empty")
    private String nom;


    @Size(min = 2, message = "client raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "client raisonSocial(intitule)  should not empty")
    private String fonction;




    @Size(min = 2, message = "client raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "client raisonSocial(intitule)  should not empty")
    private String phone;


    @Email(message = "email is not valid")
    @NotEmpty
    private String email;


    @Size(min = 2, message = "client raisonSocial(intitule) should have at least 2 characters")
    @NotEmpty(message = "client raisonSocial(intitule)  should not empty")
    private String mobile;


}
