package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.LigneCommandeFournisseur;

public interface LignecommandeFournisseurRepository extends MongoRepository<LigneCommandeFournisseur, String>{

}
