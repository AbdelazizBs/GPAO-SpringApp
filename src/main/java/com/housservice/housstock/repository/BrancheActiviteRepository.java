package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.BrancheActivite;

public interface BrancheActiviteRepository extends MongoRepository<BrancheActivite, String> {

}

