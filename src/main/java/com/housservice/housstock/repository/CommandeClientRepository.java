package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends MongoRepository<CommandeClient, String> {
    boolean existsCommandeClientByNumBcd(String numBcd);
    Page<CommandeClient> findAll(Pageable pageables);
    List<CommandeClient> findCommandeClientByid(String id);

    Optional<CommandeClient> findCommandeClientByNumBcd(String numBcd);
    Optional<CommandeClient> findCommandeClientByArticleId(String idContact ) ;

    Page<CommandeClient> findCommandeClientByMiseEnVeille(Pageable paging, boolean b);
}
