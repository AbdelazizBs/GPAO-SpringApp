package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Personne;

public interface PersonneRepository extends MongoRepository<Personne,String>{

}
