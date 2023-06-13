package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeSuivi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CommandeSuiviRepository extends MongoRepository<CommandeSuivi, String> {

    @Query( "{$or:[{'raisonSocial': {$regex : ?0}},{'rate': {$regex : ?0}} ,{'numBcd': {$regex : ?0}},{'date': {$regex : ?0}}] }")
    Page<CommandeSuivi> findCommandeByTextToFind(String textToFind, Pageable paging);
    Page<CommandeSuivi> findAll(Pageable paging);
}
