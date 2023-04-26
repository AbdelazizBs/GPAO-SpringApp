package com.housservice.housstock.repository;

import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.AffectationProduit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AffectationProduitRepository extends MongoRepository<AffectationProduit, String> {

  
    

    Optional<AffectationProduit> findAffectationProduitByPrixVenteId(String idPrixVente);

    @Query( "{$or:[{'refClient': {$regex : ?0}},{'minimunVente': {$regex : ?0}} ,{'uniteVente': {$regex : ?0}} ,{'prixVente': {$regex : ?0}}")
    Page<AffectationProduit> findAffectationPrFoduitByTextToFind(String textToFind, Pageable paging);

    Optional<AffectationProduit> findByIdProduit(String idProduit);


    Page<AffectationProduit> findAffectationByIdProduit(String id, Pageable paging);

    Page<AffectationProduit> findAffectationProduitBylistClient(String raisonSocial, Pageable paging);
}
