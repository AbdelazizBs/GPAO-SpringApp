package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.LigneCommandeClient;

public interface LigneCommandeClientRepository extends MongoRepository<LigneCommandeClient, String>{

}
