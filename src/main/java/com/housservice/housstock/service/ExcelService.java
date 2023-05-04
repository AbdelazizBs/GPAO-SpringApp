package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import com.housservice.housstock.repository.NomenclatureRepository;
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

    final PersonnelRepository personnelRepository;
    final ClientRepository clientRepository;
    final FournisseurRepository fournisseurRepository;
    final NomenclatureRepository nomenclatureRepository;
    
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public ExcelService(ClientRepository clientRepository, PersonnelRepository personnelRepository,FournisseurRepository fournisseurRepository,NomenclatureRepository nomenclatureRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.clientRepository = clientRepository;
        this.personnelRepository = personnelRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.nomenclatureRepository = nomenclatureRepository;
        
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
            boolean exist = personnelRepository.existsPersonnelByMatricule(personnel.getMatricule());
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
            boolean exist = personnelRepository.existsPersonnelByMatricule(personnel.getMatricule());
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
            boolean exist = clientRepository.existsClientByRefClientIris(client.getRefClientIris());
            if (exist) {
                clientRepository.delete(clientRepository.findClientByRefClientIris(client.getRefClientIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),client.getRefClientIris()))));
                clientRepository.save(client);
            }
            client.setDate(new Date());
            client.setMiseEnVeille(false);
            client.setContact(new ArrayList<>());
            client.setPictures(new ArrayList<>());
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
            client.setMiseEnVeille(false);
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

    
    public void saveFournisseur(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Fournisseur> fournisseurs = ExcelHelper.excelToFournisseurs(file.getInputStream());
        List<Fournisseur> fournisseurs1 = new ArrayList<>();
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getRefFrsIris() != null && fournisseur.getRefFrsIris().length() > 0) {
                fournisseurs1.add(fournisseur);
            }
        }
        for (Fournisseur fournisseur : fournisseurs1) {
            boolean exist = fournisseurRepository.existsFournisseurByRefFrsIris(fournisseur.getRefFrsIris());
            if (exist) {
                fournisseurRepository.delete(fournisseurRepository.findFournisseurByRefFrsIris(fournisseur.getRefFrsIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),fournisseur.getRefFrsIris()))));
                fournisseurRepository.save(fournisseur);
            }
            fournisseur.setDate(new Date());
            fournisseur.setMiseEnVeille(0);
            List<Contact> contacts = new ArrayList<>();
                fournisseur.setContact(contacts);
            fournisseur.setAbrege("");
            fournisseur.setAdresseBanque("");
            fournisseur.setCodeDouane("");
            fournisseur.setCodePostal("");
            fournisseur.setEmail("");
            fournisseur.setIdentifiantTva("");
            fournisseur.setLinkedin("");
            fournisseur.setNomBanque("");
            fournisseur.setRegion("");
            fournisseur.setSiteWeb("");
            fournisseur.setRne("");
            fournisseur.setRib("");
            fournisseur.setSwift("");
            fournisseur.setPictures(new ArrayList<>());
            fournisseurRepository.save(fournisseur);
        }
    }

    public void excelFormatSageToFournisseur(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Fournisseur> fournisseurs = ExcelHelper.excelFormatSageToFournisseur(file.getInputStream());
        List<Fournisseur> fournisseurs1 = new ArrayList<>();
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getRefFrsIris() != null && fournisseur.getRefFrsIris().length() > 0) {
                fournisseurs1.add(fournisseur);
            }
        }
        for (Fournisseur fournisseur : fournisseurs1) {
            fournisseur.setDate(new Date());
            fournisseur.setMiseEnVeille(0);
            List<Contact> contacts = new ArrayList<>();
            fournisseur.setContact(contacts);
            boolean exist = fournisseurRepository.existsFournisseurByRefFrsIris(fournisseur.getRefFrsIris());
            if (exist) {
                fournisseurRepository.delete(fournisseurRepository.findFournisseurByRefFrsIris(fournisseur.getRefFrsIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),fournisseur.getRefFrsIris()))));
                fournisseurRepository.save(fournisseur);
            }
            fournisseurRepository.save(fournisseur);
        }

    }
    
    
    
    public void saveNomenclature(MultipartFile file) throws IOException, ResourceNotFoundException {
        List<Nomenclature> nomenclatures = ExcelHelper.excelToNomenclatures(file.getInputStream());
        List<Nomenclature> nomenclature1 = new ArrayList<>();
        for (Nomenclature nomenclature : nomenclatures) {
            if (nomenclature.getRefIris() != null && nomenclature.getRefIris().length() > 0) {
            	nomenclature1.add(nomenclature);
            }
        }
        for (Nomenclature nomenclature : nomenclature1) {
        	//nomenclature.setDate(new Date());
        	//nomenclature.setMiseEnVeille(0);
           // List<Contact> contacts = new ArrayList<>();
            //    fournisseur.setContact(contacts);
            boolean exist = nomenclatureRepository.existsNomenclatureByRefIris(nomenclature.getRefIris());
            if (exist) {
                nomenclatureRepository.delete(nomenclatureRepository.findNomenclatureByRefIris(nomenclature.getRefIris())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                MessageFormat.format(messageHttpErrorProperties.getError0002(),nomenclature.getRefIris()))));
                nomenclatureRepository.save(nomenclature);
            }
            nomenclatureRepository.save(nomenclature);
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
    
    public byte[] getFournisseurFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("FournisseurFormatStandardExp.xlsx").getFile());
        return Files.readAllBytes(file.toPath());
    }



}

