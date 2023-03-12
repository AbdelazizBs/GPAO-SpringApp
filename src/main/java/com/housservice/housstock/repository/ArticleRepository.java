package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article,String> {
}
