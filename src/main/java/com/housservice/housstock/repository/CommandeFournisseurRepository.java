package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.CommandeFournisseur;
	
public interface CommandeFournisseurRepository extends MongoRepository<CommandeFournisseur, String>{

}
