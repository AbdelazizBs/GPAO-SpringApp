package com.housservice.housstock.repository;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends MongoRepository<Commande, String> {
    boolean existsCommandeByNumBcd(String numBcd);
    Page<Commande> findAll(Pageable pageables);
    List<Commande> findCommandeByid(String id);
    Optional<Commande> findCommandeByNumBcd(String numBcd);

    Optional<Commande> findCommandeByArticleId(String idContact ) ;

    Page<Commande> findCommandeByMiseEnVeille(Pageable paging, boolean b);

    List<Commande> findCommandeByMiseEnVeille(Boolean b);

    @Query( "{$or:[{'numBcd': {$regex : ?0}},{'fournisseur': {$regex : ?0}} ,{'article.designationMatiere': {$regex : ?0}}] }")
    Page<Commande> findCommandeByTextToFind(String textToFind, Pageable paging);
}
