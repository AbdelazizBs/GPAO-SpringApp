package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Etape;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtapeRepository extends MongoRepository<Etape,String> {
}
