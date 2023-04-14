package com.housservice.housstock.repository;

import com.housservice.housstock.model.Affectation;

import com.housservice.housstock.model.ListeMatiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AffectationRepository extends MongoRepository<Affectation, String> {

    Optional<Object> findAffectationByPrixAchatId(String idPrixAchat);
    @Query( "{$or:[{'refFournisseur': {$regex : ?0}},{'minimunAchat': {$regex : ?0}} ,{'uniteAchat': {$regex : ?0}} ,{'prixAchat': {$regex : ?0}}")

    Page<Affectation> findAffectationByTextToFind(String textToFind, Pageable paging);

    Optional<Affectation> findByIdMatiere(String idMatiere);
}
