package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Document(collection = "Contact")
public class Contact {


    @Transient
    public static final String SEQUENCE_NAME ="contact_sequence";


    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    @Size(max = 100)
    private String nom;

    @NotBlank
    @Indexed(unique = true)
    @Size(max = 100)
    private String fonction;


    @NotBlank
    @Indexed(unique = true)
    @Size(max = 100)
    private String phone;

    @NotBlank
    @Indexed(unique = true)
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Indexed(unique = true)
    @Size(max = 100)
    private String mobile;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
