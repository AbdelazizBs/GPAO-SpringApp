package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Comptes;

public interface ComptesRepository extends MongoRepository<Comptes, String> {

}
