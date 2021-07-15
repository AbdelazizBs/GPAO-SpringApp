package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.CommandeClient;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String>{

}
