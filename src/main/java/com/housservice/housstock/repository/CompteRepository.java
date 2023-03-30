package com.housservice.housstock.repository;

import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CompteRepository extends MongoRepository<Compte, String> {
    Compte findByEmail(String email);

    @Query( "{$or:[{'email': {$regex : ?0}} ,{'password': {$regex : ?0}},{'idPersonnel': {$regex : ?0}},{'role': {$regex : ?0}}]}")
    Page<Compte> findCompteByTextToFind(String textToFind, Pageable pageable);
}
