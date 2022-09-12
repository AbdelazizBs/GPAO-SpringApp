package com.housservice.housstock.repository;

import com.housservice.housstock.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String>{

    List<Article> findArticleByClient(Client client);

   Optional <Article> findArticleByDesignation(String designation);

}
