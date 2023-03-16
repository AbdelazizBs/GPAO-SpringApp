package com.housservice.housstock.repository;

import com.housservice.housstock.model.MatierePrimaire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatierePrimaireRepository extends MongoRepository<MatierePrimaire,String> {
}
