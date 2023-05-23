package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String> {
    boolean existsCommandeClientByNumBcd(String numBcd);
    Page<CommandeClient> findAll(Pageable pageables);
    List<CommandeClient> findCommandeClientByid(String id);

    Optional<CommandeClient> findCommandeClientByNumBcd(String numBcd);
    Optional<CommandeClient> findCommandeClientByArticleId(String idContact ) ;

    Page<CommandeClient> findCommandeClientByMiseEnVeille(Pageable paging, boolean b);
    @Query( "{$or:[{'numBcd': {$regex : ?0}},{'client': {$regex : ?0}} ,{'article.designationMatiere': {$regex : ?0}}] }")
    Page<CommandeClient> findCommandeClientByTextToFind(String textToFind, Pageable paging);

    CommandeClient findCommandeClientByArticle(Article article);
}
