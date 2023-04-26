package com.housservice.housstock.repository;

import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.PrixAchat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AffectationRepository extends MongoRepository<Affectation, String> {

    @Query( "{$or:[{'destination': {$regex : ?0}} ,{'uniteAchat': {$regex : ?0}},{'minimumachat': {$regex : ?0}}]}")
    Page<Affectation> findAffectationByTextToFind(String textToFind, Pageable pageable);

    Page<Affectation> findAffectationByIdmatiere(String id, Pageable paging);
    Optional<Affectation> findAffectationByPrixAchatId(String idPrixAchat);


    Page<Affectation> findAffectationByListFournisseur(String id, Pageable paging);

    List<Affectation> findByPrixAchat(PrixAchat prixAchat);
}
