package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Categorie;
	
public interface CategorieRepository extends MongoRepository<Categorie, String>{

}
