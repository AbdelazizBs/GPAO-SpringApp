package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Client;

public interface ClientRepository extends MongoRepository <Client, String> {

}
