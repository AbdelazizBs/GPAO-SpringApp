package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Entreprise;

public interface EntrepriseRepository extends MongoRepository<Entreprise, String>{

}
