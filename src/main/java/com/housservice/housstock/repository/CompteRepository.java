package com.housservice.housstock.repository;

import com.housservice.housstock.model.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompteRepository extends MongoRepository<Compte, String> {
    Optional<Compte> findByEmail(String email);
}
