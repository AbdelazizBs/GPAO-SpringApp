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
    @Override
    public void deleteContactClient(String idContact) throws ResourceNotFoundException {
        Client client = clientRepository.findClientByContactId(idContact)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
        Contact contact = contactRepository.findById(idContact)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idContact)));
        for (Contact contact1  : client.getContact())
        { if (contact1.equals(contact)) { client.getContact().remove(contact); } }
        client.setContact(new ArrayList<>());
        clientRepository.save(client);
        contactRepository.deleteById(idContact);

    }
}
