package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Article;

public interface ArticleRepository extends MongoRepository<Article, String>{

}
