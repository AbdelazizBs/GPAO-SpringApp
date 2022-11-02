package com.housservice.housstock.repository;

import com.housservice.housstock.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Article;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String>{

    List<Article> findArticleByClientId(String idClient);

    Page<Article> findArticleByMiseEnVeille(int i, Pageable pageable);
   Optional <Article> findArticleByDesignation(String designation);

}
