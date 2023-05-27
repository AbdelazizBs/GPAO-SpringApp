package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Etape;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EtapeRepository extends MongoRepository<Etape,String> {
    Etape findEtapeByNomEtape(String etat);

    List<Etape> findEtapeByTypeEtape(String machine);

    void deleteByNomEtape(String autre);
}
