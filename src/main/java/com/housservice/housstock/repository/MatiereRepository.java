package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Matiere;

public interface MatiereRepository extends MongoRepository<Matiere, String>{

}
