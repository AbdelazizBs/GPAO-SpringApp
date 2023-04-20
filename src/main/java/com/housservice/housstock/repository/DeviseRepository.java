package com.housservice.housstock.repository;

import com.housservice.housstock.model.Devise;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviseRepository extends MongoRepository<Devise, String> {
}
