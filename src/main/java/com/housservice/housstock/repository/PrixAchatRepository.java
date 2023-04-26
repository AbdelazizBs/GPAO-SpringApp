package com.housservice.housstock.repository;

import com.housservice.housstock.model.PrixAchat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrixAchatRepository extends MongoRepository<PrixAchat, String> {
}
