package com.housservice.housstock.repository;


import com.housservice.housstock.model.ListeMatiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ListeMatiereRepository extends MongoRepository<ListeMatiere, String> {
    Page<ListeMatiere> findAllByType(Pageable paging, String Type);
    boolean existsListeMatiereByDesignation(String designation);
    @Query( "{$or:[{'designation': {$regex : ?0}},{'longueur': {$regex : ?0}} ,{'v': {$regex : ?0}}] }")
    Page<ListeMatiere> findListeMatiereByTextToFind(String textToFind, Pageable paging);
}
