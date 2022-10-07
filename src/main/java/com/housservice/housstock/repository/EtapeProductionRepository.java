package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.EtapeProduction;

import java.util.Optional;

public interface EtapeProductionRepository extends MongoRepository<EtapeProduction, String>{
Optional<EtapeProduction> findByNomEtape(String s);
}
