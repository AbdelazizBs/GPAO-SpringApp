package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.SecteurActivite;

public interface SecteurActiviteRepository extends MongoRepository<SecteurActivite, String> {

}
