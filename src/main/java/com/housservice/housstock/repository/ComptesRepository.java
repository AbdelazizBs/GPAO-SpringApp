package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Comptes;

import javax.validation.constraints.Email;

public interface ComptesRepository extends MongoRepository<Comptes, String> {
    Comptes findByEmail(String email);

}
