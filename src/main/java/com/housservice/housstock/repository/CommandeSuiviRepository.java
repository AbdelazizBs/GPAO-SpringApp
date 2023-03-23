package com.housservice.housstock.repository;

import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.CommandeSuivi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandeSuiviRepository extends MongoRepository<CommandeSuivi, String> {


    Page<CommandeSuivi> findAll(Pageable paging);
}
