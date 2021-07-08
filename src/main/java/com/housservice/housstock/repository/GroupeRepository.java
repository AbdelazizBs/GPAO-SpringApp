package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Groupe;

public interface GroupeRepository extends MongoRepository<Groupe, String>{

}
