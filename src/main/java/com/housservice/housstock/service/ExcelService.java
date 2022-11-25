package com.housservice.housstock.service;

import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    final
    PersonnelRepository personnelRepository;

    final
    ClientRepository clientRepository;

    public ExcelService(ClientRepository clientRepository, PersonnelRepository personnelRepository) {
        this.clientRepository = clientRepository;
        this.personnelRepository = personnelRepository;
    }


    public void savePersonnel(MultipartFile file) throws IOException
    {

        List<Personnel> personnels = ExcelHelper.excelToPersonnels(file.getInputStream());
        personnelRepository.saveAll(personnels);

    }

    public void saveClient(MultipartFile file) throws IOException
    {

        List<Client> clients = ExcelHelper.excelToClients(file.getInputStream());
        clientRepository.saveAll(clients);

    }
    public void excelFormatSageToClient(MultipartFile file) throws IOException
    {

        List<Client> clients = ExcelHelper.excelFormatSageToClient(file.getInputStream());
        clientRepository.saveAll(clients);

    }


}

