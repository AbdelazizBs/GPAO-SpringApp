package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String>{

    Page<CommandeClient> findCommandeClientByClosed(boolean closed, Pageable pageable);

    Optional<CommandeClient> findCommandeClientByLigneCommandeClient(LigneCommandeClient ligneCommandeClient);

}
