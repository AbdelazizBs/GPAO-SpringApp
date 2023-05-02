package com.housservice.housstock.mapper;

import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.dto.ContactDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-02T09:36:09+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl extends ContactMapper {

    @Override
    public ContactDto toContactDto(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactDto contactDto = new ContactDto();

        contactDto.setId( contact.getId() );
        contactDto.setNom( contact.getNom() );
        contactDto.setFonction( contact.getFonction() );
        contactDto.setPhone( contact.getPhone() );
        contactDto.setEmail( contact.getEmail() );
        contactDto.setMobile( contact.getMobile() );

        updateContactDto( contact, contactDto );

        return contactDto;
    }

    @Override
    public Contact toContact(ContactDto contactDto) {
        if ( contactDto == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setId( contactDto.getId() );
        contact.setNom( contactDto.getNom() );
        contact.setFonction( contactDto.getFonction() );
        contact.setPhone( contactDto.getPhone() );
        contact.setEmail( contactDto.getEmail() );
        contact.setMobile( contactDto.getMobile() );

        updateContact( contactDto, contact );

        return contact;
    }
}
