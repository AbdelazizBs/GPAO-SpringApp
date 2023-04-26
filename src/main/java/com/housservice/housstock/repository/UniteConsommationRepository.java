package com.housservice.housstock.repository;

import com.housservice.housstock.model.UniteConsommation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniteConsommationRepository extends MongoRepository<UniteConsommation, String> {
}
