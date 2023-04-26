package com.housservice.housstock.repository;

import com.housservice.housstock.model.PrixAchat;
import com.housservice.housstock.model.PrixVente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrixVenteRepository extends MongoRepository<PrixVente, String> {
}
