package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Ventes;

public interface VentesRepository extends MongoRepository<Ventes, String>{

}
