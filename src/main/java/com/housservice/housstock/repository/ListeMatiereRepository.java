package com.housservice.housstock.repository;


import com.housservice.housstock.model.ListeMatiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListeMatiereRepository extends MongoRepository<ListeMatiere, String> {
   
    ListeMatiere findByDesignation(String designation);

    List<ListeMatiere> findAllByDesignation(String designation);

    boolean existsListeMatiereByDesignation(String designation);

    Page<ListeMatiere> findListeMatiereByTextToFind(String textToFind, Pageable paging);
}
