package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.MvtStk;

public interface MvtStkRepository extends MongoRepository<MvtStk, String>{

}
