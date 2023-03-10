package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String> {

    Page<CommandeClient> findCommandeClientByClosed(boolean closed, Pageable pageable);


}
