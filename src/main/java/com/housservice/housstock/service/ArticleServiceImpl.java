package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;

import com.housservice.housstock.model.Article;

import com.housservice.housstock.model.Commande;

import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;

@Service
public class ArticleServiceImpl implements ArticleService{
    private  final MessageHttpErrorProperties messageHttpErrorProperties;
    final CommandeRepository commandeRepository ;

    final ArticleRepository articleRepository ;

    public ArticleServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, CommandeRepository commandeRepository, ArticleRepository articleRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.commandeRepository = commandeRepository;
        this.articleRepository = articleRepository;
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

