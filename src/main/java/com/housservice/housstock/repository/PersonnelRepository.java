package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.housservice.housstock.model.Personnel;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.function.Predicate;

public interface PersonnelRepository extends MongoRepository<Personnel, String>  {
Personnel findByNom(String s);
    Personnel findByCompte(Comptes comptes);
    Page<Personnel> findPersonnelByMiseEnVeille(boolean b, Pageable pageable);


//    @Query( "{'nom': {$regex : ?0}}"+
//            "{'prenom': {$regex : ?0}}"+
//            "{'sexe': {$regex : ?0}}"+
//            "{'poste': {$regex : ?0}}"+
//            "{'adresse': {$regex : ?0}}")

    @Query( "{'nom': {$regex : ?0}}")
    Page<Personnel> findPersonnelByTextToFind(String textToFind, Pageable pageable);


}