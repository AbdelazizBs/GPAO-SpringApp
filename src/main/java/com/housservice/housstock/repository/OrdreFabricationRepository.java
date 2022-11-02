package com.housservice.housstock.repository;

import com.housservice.housstock.model.OrdreFabrication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdreFabricationRepository extends MongoRepository<OrdreFabrication,String > {
}
