package com.housservice.housstock.service;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ContactServiceImpl implements ContactService {

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    final
    ClientRepository clientRepository ;

    final
    ContactRepository contactRepository ;

    public ContactServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, ClientRepository clientRepository, ContactRepository contactRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.clientRepository = clientRepository;
        this.contactRepository = contactRepository;
    }



}
