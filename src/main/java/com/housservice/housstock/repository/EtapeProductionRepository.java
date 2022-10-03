package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.EtapeProduction;

public interface EtapeProductionRepository extends MongoRepository<EtapeProduction, String>{
EtapeProduction findByNomEtape(String s);
}
