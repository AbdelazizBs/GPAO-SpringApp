package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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


    public void savePersonnel(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Personnel> personnels = ExcelHelper.excelToPersonnels(file.getInputStream());
        List<Personnel> personnels1 = new ArrayList<>();
        for (Personnel personnel : personnels) {
            if (personnel.getMatricule() != null && personnel.getMatricule().length() > 0) {
                personnels1.add(personnel);
            }
        }
        for (Personnel personnel : personnels) {
            boolean exist = personnelRepository.existsPersonnelByCinAndMatricule(personnel.getCin(),personnel.getMatricule());
            if (exist) {
                personnelRepository.delete(personnelRepository.findPersonnelByCin(personnel.getCin())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(), personnel.getCin()))));
                personnelRepository.save(personnel);
            }
            personnelRepository.save(personnel);
        }
    }

    public void savePersonnelFromSage(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Personnel> personnels = ExcelHelper.excelFormatSageToPersonnel(file.getInputStream());
        List<Personnel> personnels1 = new ArrayList<>();
        for (Personnel personnel : personnels) {
            if (personnel.getMatricule() != null && personnel.getMatricule().length() > 0) {
                personnels1.add(personnel);
            }
        }
        for (Personnel personnel : personnels1) {
            boolean exist = personnelRepository.existsPersonnelByCinAndMatricule(personnel.getCin(),personnel.getMatricule());
            if (exist) {
                personnelRepository.delete(personnelRepository.findPersonnelByCin(personnel.getCin())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(), personnel.getCin()))));
                personnelRepository.save(personnel);
            }
            personnelRepository.save(personnel);
        }
    }


    public void saveClient(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Client> clients = ExcelHelper.excelToClients(file.getInputStream());
        List<Client> clients1 = new ArrayList<>();
        for (Client client : clients) {
            if (client.getRefClientIris() != null && client.getRefClientIris().length() > 0) {
                clients1.add(client);
            }
        }
        for (Client client : clients1) {
            client.setDate(new Date());
            client.setMiseEnVeille(0);
            List<Contact> contacts = new ArrayList<>();
                client.setContact(contacts);
            boolean exist = clientRepository.existsClientByRefClientIris(client.getRefClientIris());
            if (exist) {
                clientRepository.delete(clientRepository.findClientByRefClientIris(client.getRefClientIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),client.getRefClientIris()))));
                clientRepository.save(client);
            }
            clientRepository.save(client);
        }


    }

    public void excelFormatSageToClient(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Client> clients = ExcelHelper.excelFormatSageToClient(file.getInputStream());
        List<Client> clients1 = new ArrayList<>();
        for (Client client : clients) {
            if (client.getRefClientIris() != null && client.getRefClientIris().length() > 0) {
                clients1.add(client);
            }
        }
        for (Client client : clients1) {
            client.setDate(new Date());
            client.setMiseEnVeille(0);
            List<Contact> contacts = new ArrayList<>();
            client.setContact(contacts);
            boolean exist = clientRepository.existsClientByRefClientIris(client.getRefClientIris());
            if (exist) {
                clientRepository.delete(clientRepository.findClientByRefClientIris(client.getRefClientIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),client.getRefClientIris()))));
                clientRepository.save(client);
            }
            clientRepository.save(client);
        }

    }

    public byte[] getPersonnelFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("PersonnelFormatStandardExp.xlsx").getFile());
        return Files.readAllBytes(file.toPath());
    }
    public byte[] getClientFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ClientFormatStandardExp.xlsx").getFile());
        return Files.readAllBytes(file.toPath());
    }



}

