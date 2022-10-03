package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Utilisateur;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
Utilisateur findByNom(String s);
    Utilisateur findByCompte(Comptes comptes);

}