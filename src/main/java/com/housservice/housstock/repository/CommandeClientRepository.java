package com.housservice.housstock.repository;

import com.housservice.housstock.model.EtatMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.CommandeClient;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String>{

    Page<CommandeClient> findCommandeClientsByEtat(String s, Pageable pageable);

}
