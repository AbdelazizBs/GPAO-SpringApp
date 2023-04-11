package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClientSuivi;
import com.housservice.housstock.model.CommandeSuivi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandeClientSuiviRepository extends MongoRepository<CommandeClientSuivi, String> {
    Page<CommandeClientSuivi> findAll(Pageable paging);
}
