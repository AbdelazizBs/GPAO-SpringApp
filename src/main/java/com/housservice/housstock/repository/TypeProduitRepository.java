package com.housservice.housstock.repository;


import com.housservice.housstock.model.TypeProduit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeProduitRepository extends MongoRepository<TypeProduit, String> {
}
