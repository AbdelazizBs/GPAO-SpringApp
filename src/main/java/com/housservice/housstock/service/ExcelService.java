package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

@Service
public class ExcelService {

    final
    PersonnelRepository personnelRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    final
    ClientRepository clientRepository;

    public ExcelService(ClientRepository clientRepository, PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.clientRepository = clientRepository;
        this.personnelRepository = personnelRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


    public void savePersonnel(MultipartFile file) throws IOException
    {

        List<Personnel> personnels = ExcelHelper.excelToPersonnels(file.getInputStream());
if (personnelRepository.count()>0){
    personnels.stream().map(personnel -> {
        try {
            Personnel personnel1 = personnelRepository.findPersonnelByCin(personnel.getCin()).orElseThrow(() -> new ResourceNotFoundException(
                    MessageFormat.format(messageHttpErrorProperties.getError0002(), personnel.getCin())));
            if(personnel1!= null &&personnel1.equals(personnel)){
                personnelRepository.delete(personnel1);
            }

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return personnelRepository.saveAll(personnels);

    });
}else
    personnelRepository.saveAll(personnels);

    }

    public void saveClient(MultipartFile file) throws IOException
    {

        List<Client> clients = ExcelHelper.excelToClients(file.getInputStream());
        if (clientRepository.count()>0){
        clients.stream().map(client -> {
            try {
                Client client1 = clientRepository.findClientByRaisonSocial(client.getRaisonSocial()).orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format(messageHttpErrorProperties.getError0002(), client.getRaisonSocial())));
                if(client1.equals(client)){
                    clientRepository.delete(client1);
                }
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            return clientRepository.saveAll(clients);
        });
        }
        else
            clientRepository.saveAll(clients);


    }
    public void excelFormatSageToClient(MultipartFile file) throws IOException
    {

        List<Client> clients = ExcelHelper.excelFormatSageToClient(file.getInputStream());
        if (clientRepository.count()>0){
            clients.stream().map(client -> {
                try {
                    Client client1 = clientRepository.findClientByRaisonSocial(client.getRaisonSocial()).orElseThrow(() -> new ResourceNotFoundException(
                            MessageFormat.format(messageHttpErrorProperties.getError0002(), client.getRaisonSocial())));
                    if(client1.equals(client)){
                        clientRepository.delete(client1);
                    }
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return clientRepository.saveAll(clients);
            });
        }
        else
            clientRepository.saveAll(clients);

    }


}

