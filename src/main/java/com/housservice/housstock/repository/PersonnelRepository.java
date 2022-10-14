package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Personnel;

public interface PersonnelRepository extends MongoRepository<Personnel, String> {
Personnel findByNom(String s);
    Personnel findByCompte(Comptes comptes);

}