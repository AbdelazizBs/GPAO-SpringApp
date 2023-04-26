package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.*;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.*;
import com.housservice.housstock.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
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
    private final ArticleRepository articleRepository;
    private final CommandeSuiviRepository commandeSuiviRepository;

    private final MatiereRepository matiereRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository, FournisseurRepository fournisseurRepository, MessageHttpErrorProperties messageHttpErrorProperties, ArticleRepository articleRepository, CommandeSuiviRepository commandeSuiviRepository, MatiereRepository matiereRepository) {
        this.commandeRepository = commandeRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.articleRepository = articleRepository;
        this.commandeSuiviRepository = commandeSuiviRepository;
        this.matiereRepository = matiereRepository;
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
            pageTuts =  commandeRepository.findCommandeByMiseEnVeille(paging,false);
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
    public void createNewCommande(CommandeDto commandeDto) throws ResourceNotFoundException {
        commandeDto.setMiseEnVeille(false);

        List<Article> articles = new ArrayList<>();
        if (commandeDto.getArticle()==null){
            commandeDto.setArticle(articles);
        }
        Commande commande = CommandeMapper.MAPPER.toCommande(commandeDto);
        commandeRepository.save(commande);
    }

    @Override
    public void UpdateCommande(Commande commande,String id) throws ResourceNotFoundException {

        Commande commandes = getCommandeById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
        commandes.setCommentaire(commande.getCommentaire());
        commandes.setFournisseur(commande.getFournisseur());
        commandes.setNumBcd(commande.getNumBcd());
        commandes.setDateCommande(commande.getDateCommande());
        commandeRepository.save(commandes);
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

    @Override
    public void addArticleCommande(ArticleDto articleDto, String idCommande) throws ResourceNotFoundException {
        Commande commande = getCommandeById(idCommande)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idCommande)));
        List<Article> articles = new ArrayList<>();
        Article article1 = ArticleMapper.MAPPER.toArticle(articleDto);
        if(commande.getArticle()==null){
            articles.add(article1);
            articleRepository.save(article1);
            commande.setArticle(articles);
            commandeRepository.save(commande);
        }
        articleRepository.save(article1);
        articles.add(article1);
        articles.addAll(commande.getArticle());
        commande.setArticle(articles);
        commandeRepository.save(commande);
    }


    @Override
    public void updateArticleCommande(ArticleDto articleDto, String idArticle) throws ResourceNotFoundException {
        Commande commande =commandeRepository.findCommandeByArticleId(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  articleDto)));
        Article articleToUpdate = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  articleDto.getId())));
        commande.getArticle().removeIf(article1 -> article1.equals(articleToUpdate));
        articleToUpdate.setCommentaire(articleDto.getCommentaire());
        articleToUpdate.setPrix(articleDto.getPrix());
        articleToUpdate.setQuantite(articleDto.getQuantite());
        articleToUpdate.setPrixUnitaire(articleDto.getPrixUnitaire());
        articleToUpdate.setDesignationMatiere(articleDto.getDesignationMatiere());
        articleToUpdate.setDateLivraison(articleDto.getDateLivraison());
        articleRepository.save(articleToUpdate);
        commande.getArticle().add(articleToUpdate);
        commandeRepository.save(commande);

    }

    @Override
    public void deleteArticleCommande(String idArticle) throws ResourceNotFoundException {
        Commande commande= commandeRepository.findCommandeByArticleId(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
        List<Article> articleList = commande.getArticle();
        articleList.removeIf(c -> c.equals(article));
        commande.setArticle(articleList);
        commandeRepository.save(commande);
        articleRepository.deleteById(idArticle);
    }
    @Override
    public List<String> getAllMatiere() {
        List<Matiere> matieres = matiereRepository.findAll();
        return matieres.stream()
                .map(Matiere::getDesignation)
                .collect(Collectors.toList());
    }
    @Override
    public void addMatiere(String designation) throws ResourceNotFoundException {
        MatiereDto matiereDto = new MatiereDto();
        matiereDto.setDesignation(designation);
        Matiere matiere = MatiereMapper.MAPPER.toMatiere(matiereDto);
        matiereRepository.save(matiere);
    }

    @Override
    public void miseEnVeille(String id,CommandeSuiviDto commandeSuiviDto) throws ResourceNotFoundException {

        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        commande.setMiseEnVeille(true);
        String name= commande.getFournisseur();
        Fournisseur fournisseur = fournisseurRepository.findFournisseurByRaisonSocial(name).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),name)));
        fournisseur.setRate(commandeSuiviDto.getRate());
        fournisseurRepository.save(fournisseur);
        commandeRepository.save(commande);
        CommandeSuivi commandeSuivi = CommandeSuiviMapper.MAPPER.toCommandeSuivi(commandeSuiviDto);
        commandeSuivi.setRaisonSocial(fournisseur.getRaisonSocial());
        commandeSuiviRepository.save(commandeSuivi);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCommandeNotActive(int page, int size) {
        try {

            List<CommandeSuiviDto> commandes = new ArrayList<CommandeSuiviDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<CommandeSuivi> pageTuts;
            pageTuts =  commandeSuiviRepository.findAll(paging);
            commandes = pageTuts.getContent().stream().map(commandeSuivi -> CommandeSuiviMapper.MAPPER.toCommandeSuiviDto(commandeSuivi)).collect(Collectors.toList());
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


}

