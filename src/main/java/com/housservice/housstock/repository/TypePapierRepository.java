package com.housservice.housstock.repository;


import com.housservice.housstock.model.TypePapier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypePapierRepository extends MongoRepository<TypePapier, String> {
}
