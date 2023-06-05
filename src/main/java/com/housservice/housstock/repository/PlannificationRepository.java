package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Plannification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlannificationRepository extends MongoRepository<Plannification,String> {
    Page<Plannification> findPlannificationByEtat(boolean b, Pageable paging);

    @Query("{$or:[{'ligneCommandeClient.produit.ref': {$regex : ?0}}, {'ligneCommandeClient.designationMatiere': {$regex : ?0}}, {'ligneCommandeClient.prixUnitaire': {$regex : ?0}}] }")
    Page<Plannification> findPlannificationByTextToFind(String textToFind, Pageable paging);

    List<Plannification> findPlannificationByEtat(boolean b);
}
