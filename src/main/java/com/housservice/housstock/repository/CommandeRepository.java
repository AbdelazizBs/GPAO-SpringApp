package com.housservice.housstock.repository;

import com.housservice.housstock.model.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommandeRepository extends MongoRepository<Commande, String> {
    boolean existsCommandeByNumBcd(String numBcd);
    Page<Commande> findAll(Pageable pageables);
    List<Commande> findCommandeByid(String id);

    Optional<Commande> findCommandeByNumBcd(String numBcd);
}
