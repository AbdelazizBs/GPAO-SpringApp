package com.housservice.housstock.repository;

import com.housservice.housstock.model.Matiere;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatiereRepository extends MongoRepository<Matiere,String> {

}
