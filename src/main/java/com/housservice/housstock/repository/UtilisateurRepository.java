package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Utilisateur;


public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {

}

