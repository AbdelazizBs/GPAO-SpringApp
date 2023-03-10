package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.CommandeMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.CommandeDto;
import com.housservice.housstock.repository.CommandeRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class CommandeServiceImpl implements CommandeService{
    private final CommandeRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public CommandeServiceImpl(CommandeRepository commandeRepository, FournisseurRepository fournisseurRepository, MessageHttpErrorProperties messageHttpErrorProperties) {

        this.commandeRepository = commandeRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

    @Override
    public Optional<Commande> getCommandeById(String id) {
        return commandeRepository.findById(id);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCommande(int page, int size) {
        try {
            List<CommandeDto> commandes = new ArrayList<CommandeDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Commande> pageTuts;
            pageTuts =  commandeRepository.findAll(paging);
            commandes = pageTuts.getContent().stream().map(commande -> CommandeMapper.MAPPER.toCommandeDto(commande)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("commandes", commandes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public void createNewCommande(String fournisseur, String numBcd,String dateCommande,String commentaire ) throws ResourceNotFoundException {
        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setDateCommande(dateCommande);
        commandeDto.setCommentaire(commentaire);
        commandeDto.setFournisseur(fournisseur);
        commandeDto.setNumBcd(numBcd);
        Commande commande = CommandeMapper.MAPPER.toCommande(commandeDto);
        commandeRepository.save(commande);
    }

    @Override
    public void UpdateCommande(String dateCommande, String commentaire, String numBcd, String fournisseur,String id) throws ResourceNotFoundException {
        if (commentaire.isEmpty() || numBcd.isEmpty() || fournisseur.isEmpty()) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Commande commande = getCommandeById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
        commande.setCommentaire(commentaire);
        commande.setFournisseur(fournisseur);
        commande.setNumBcd(numBcd);
        commande.setDateCommande(dateCommande);
        commandeRepository.save(commande);
    }

    @Override
    public void deleteCommande(Commande commande) {
        commandeRepository.delete(commande);
    }

    @Override
    public void deleteCommandeSelected(List<String> idCommandesSelected) {
        for (String id : idCommandesSelected){
            commandeRepository.deleteById(id);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>>  getIdCommandes(String numBcd) throws ResourceNotFoundException {
        Commande commande = commandeRepository.findCommandeByNumBcd(numBcd).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),numBcd)));
        Map<String, Object> response = new HashMap<>();
        response.put("idCommande", commande.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public int getallCommande() {
        try {
            List<Commande> commande = commandeRepository.findAll();
            return (int) commande.stream().count();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;


        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveCommande(int page, int size, String field, String order) {
        try {
            List<CommandeDto> commandeDtos ;
            Page<Commande> pageTuts;
            if (order.equals("1")){
                pageTuts = commandeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = commandeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            commandeDtos = pageTuts.getContent().stream().map(commande -> {
                return CommandeMapper.MAPPER.toCommandeDto(commande);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("commandes", commandeDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<String> getAllFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findFournisseurByMiseEnVeille(false);
        return fournisseurs.stream()
                .map(Fournisseur::getRaisonSocial)
                .collect(Collectors.toList());
    }

    public ResponseEntity<byte[]> RecordReport(String id) {
        try{
            List<Commande> commande= commandeRepository.findCommandeByid(id);
            File file = ResourceUtils.getFile("classpath:Commande.jrxml");
            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(commande);
            Map<String ,Object> parameter = new HashMap<>();
            parameter.put("CreatedBy","Hellotest");
            JasperPrint print = JasperFillManager.fillReport(report, parameter,dataSource);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "employees-details.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(print), headers, HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

