package com.housservice.housstock.repository;

import com.housservice.housstock.model.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommandeRepository extends MongoRepository<Commande, String> {
    boolean existsCommandeByNumBcd(String numBcd);

    Optional<Commande> findCommandeByNumBcd(String numBcd);
}
