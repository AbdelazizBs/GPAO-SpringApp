package com.housservice.housstock.repository;


import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends MongoRepository<Produit, String> {
    Page<Produit> findAllByType(Pageable paging, String type);
    @Query( "{$or:[{'designation': {$regex : ?0}},{'dateCreation': {$ref : ?0}} ,{'type': {$regex : ?0}}] }")
    Page<Produit> findProduitByTextToFind(String textToFind, Pageable paging);

    boolean existsProduitByDesignation(String designation);

    Produit findByDesignation(String designation);

    List<Produit> findAllByDesignation(String designation);

    Optional<Produit> findProduitByPictures(Picture picture);

    Optional<Produit> findProduitByRef(String ref);
}
