package com.housservice.housstock.repository;

import com.housservice.housstock.model.UniteVente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniteVenteRepoitory extends MongoRepository<UniteVente, String> {
}
