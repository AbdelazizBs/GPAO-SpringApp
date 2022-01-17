package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.LigneVente;

public interface LigneVenteRepository extends MongoRepository<LigneVente, String>{
	
}
