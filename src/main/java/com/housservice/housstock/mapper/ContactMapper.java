package com.housservice.housstock.mapper;


import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.dto.ContactDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    public static ContactMapper  MAPPER = Mappers.getMapper(ContactMapper.class);

    public abstract ContactDto toContactDto(Contact contact);

    public abstract Contact toContact(ContactDto  contactDto);



    @AfterMapping
    void updateContactDto(final Contact contact, @MappingTarget final ContactDto contactDto)   {

    }

    @AfterMapping
    void updateContact(final ContactDto  contactDto, @MappingTarget final Contact contact) {

    }
}