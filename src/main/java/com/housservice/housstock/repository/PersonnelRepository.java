package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Personnel;

public interface PersonnelRepository extends MongoRepository<Personnel, String>{

}
