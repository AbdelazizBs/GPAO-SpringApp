package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ArticleMapper;
import com.housservice.housstock.mapper.CommandeMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.CommandeDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CommandeRepository;
import com.housservice.housstock.repository.FournisseurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class CommandeServiceImpl implements CommandeService{
    private final CommandeRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public CommandeServiceImpl(CommandeRepository commandeRepository, FournisseurRepository fournisseurRepository, ArticleRepository articleRepository, MessageHttpErrorProperties messageHttpErrorProperties) {

        this.commandeRepository = commandeRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;

    }

    @Override
    public Optional<Commande> getCommandeById(String id) {
        return commandeRepository.findById(id);
    }

    @Override
    public void createNewCommande(Date date,String commentaire, String numBcd, String fournisseur) throws ResourceNotFoundException {

        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setDate(date);
        commandeDto.setCommentaire(commentaire);
        commandeDto.setFournisseur(fournisseur);
        commandeDto.setNumBcd(numBcd);
        List<Article> articles = new ArrayList<>();
        if (commandeDto.getArticle()==null){
            commandeDto.setArticle(articles);
        }
        Commande  commande = CommandeMapper.MAPPER.toCommande(commandeDto);
        commandeRepository.save(commande);



    }

    @Override
    public void UpdateCommande(String idCommande, String commentaire, String numBcd, String fournisseur) throws ResourceNotFoundException {
        if (commentaire.isEmpty() || numBcd.isEmpty() || fournisseur.isEmpty()     ) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Commande commande = getCommandeById(idCommande)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idCommande)));
        if (!commande.getNumBcd().equals(numBcd)) {
            throw new IllegalArgumentException("Error Id!!");
        }
        commande.setCommentaire(commentaire);
        commande.setFournisseur(fournisseur);
        commande.setNumBcd(fournisseur);
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
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        return fournisseurs.stream()
                .map(Fournisseur::getRefFournisseurIris)
                .collect(Collectors.toList());
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
        for (Article article1  : commande.getArticle())
        { if (article1.equals(article)) { commande.getArticle().remove(article); } }
        commande.setArticle(new ArrayList<>());
        commandeRepository.save(commande);
        articleRepository.deleteById(idArticle);

    }
    }



