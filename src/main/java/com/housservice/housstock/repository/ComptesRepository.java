package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Comptes;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface ComptesRepository extends MongoRepository<Comptes, String> {
    Optional<Comptes> findByEmail(String email);

    boolean existsByEmail(String email) ;

    Page<Comptes> findComptesByEnVeille(boolean enVeille, Pageable pageable);



}
