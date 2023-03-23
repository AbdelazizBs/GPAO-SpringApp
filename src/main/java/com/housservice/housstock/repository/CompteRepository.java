package com.housservice.housstock.repository;

import com.housservice.housstock.model.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompteRepository extends MongoRepository<Compte, String> {
    Compte findByEmail(String email);
}
