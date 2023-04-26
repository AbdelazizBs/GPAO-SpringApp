package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String>{

    List<Article> findArticleByClientId(String idClient);

    Page<Article> findArticleByMiseEnVeille(int i, Pageable pageable);
   Optional <Article> findArticleByDesignation(String designation);

    @Query( "{$or:[{'designation': {$regex : ?0}} ,{'referenceIris': {$regex : ?0}} ,{'refClient': {$regex : ?0}},{'typeProduit': {$regex : ?0}},{'numFicheTechnique': {$regex : ?0}}] }")
    Page<Article> findArticleByTextToFindAndMiseEnVeille(String textToFind, int b, Pageable pageable);
}
