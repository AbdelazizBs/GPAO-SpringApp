package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;

@Service
public class ExcelService {

    final
    PersonnelRepository personnelRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    final
    ClientRepository clientRepository;
    
    final
    FournisseurRepository fournisseurRepository;

    public ExcelService(ClientRepository clientRepository, PersonnelRepository personnelRepository, FournisseurRepository fournisseurRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.clientRepository = clientRepository;
        this.personnelRepository = personnelRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


    public void savePersonnel(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Personnel> personnels = ExcelHelper.excelToPersonnels(file.getInputStream());
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

    public byte[] getPersonnelFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("PersonnelFormatStandardExp.xlsx").getFile());
        return Files.readAllBytes(file.toPath());
    }

    
    // Gestion fichier excel pour fournisseur
    
    public byte[] getFournisseurFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("FournisseurFormatStandardExp.xlsx").getFile());
        return Files.readAllBytes(file.toPath());
    }
    
    public void saveFournisseur(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Fournisseur> fournisseurs = ExcelHelper.excelToFournisseurs(file.getInputStream());
        for (Fournisseur fournisseur : fournisseurs) {
            boolean exist = fournisseurRepository.existsFournisseurByRefFrsIrisAndIntitule(fournisseur.getRefFrsIris(),fournisseur.getIntitule());
            if (exist) {
                fournisseurRepository.delete(fournisseurRepository.findByRefFrsIris(fournisseur.getRefFrsIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseur.getRefFrsIris()))));
                fournisseurRepository.save(fournisseur);
            }
            fournisseurRepository.save(fournisseur);
        }
    }

    public void saveFournisseurFromSage(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Fournisseur> fournisseurs = ExcelHelper.excelFormatSageToFournisseur(file.getInputStream());
        for (Fournisseur fournisseur : fournisseurs) {
            boolean exist = fournisseurRepository.existsFournisseurByRefFrsIrisAndIntitule(fournisseur.getRefFrsIris(),fournisseur.getIntitule());
            if (exist) {
                fournisseurRepository.delete(fournisseurRepository.findByRefFrsIris(fournisseur.getRefFrsIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseur.getRefFrsIris()))));
                fournisseurRepository.save(fournisseur);
            }
            fournisseurRepository.save(fournisseur);
        }
    }


}

