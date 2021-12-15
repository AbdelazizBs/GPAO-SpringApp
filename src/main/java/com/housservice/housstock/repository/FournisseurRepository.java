package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Fournisseur;

public interface FournisseurRepository extends MongoRepository<Fournisseur, String>{

}
