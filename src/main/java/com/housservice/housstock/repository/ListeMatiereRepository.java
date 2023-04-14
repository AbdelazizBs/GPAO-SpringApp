package com.housservice.housstock.repository;


import com.housservice.housstock.model.ListeMatiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ListeMatiereRepository extends MongoRepository<ListeMatiere, String> {
   
    ListeMatiere findByDesignation(String designation);

    List<ListeMatiere> findAllByDesignation(String designation);

    boolean existsListeMatiereByDesignation(String designation);
    @Query( "{$or:[{'designation': {$regex : ?0}},{'longueur': {$regex : ?0}} ,{'grammage': {$regex : ?0}}] }")
    Page<ListeMatiere> findListeMatiereByTextToFind(String textToFind, Pageable paging);


    Page<ListeMatiere> findAllByType(Pageable paging, String type);
}
