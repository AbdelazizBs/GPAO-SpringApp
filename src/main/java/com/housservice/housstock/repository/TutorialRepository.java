package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Tutorial;

public interface TutorialRepository extends MongoRepository<Tutorial, String>{

}
