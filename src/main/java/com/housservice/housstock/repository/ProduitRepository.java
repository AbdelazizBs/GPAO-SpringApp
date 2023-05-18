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
    @Query( "{$or:[{'ref': {$regex : ?0}},{'designation': {$regex : ?0}} ,{'type': {$regex : ?0}}] }")
    Page<Produit> findProduitByTextToFind(String textToFind, Pageable paging);

    boolean existsProduitByDesignation(String designation);

    Produit findByDesignation(String designation);

    List<Produit> findAllByDesignation(String designation);


    Optional<Produit> findProduitByRef(String ref);

    Optional<Produit> findProduitByPicturesId(String idPic);

    Page<Produit> findProduitByMiseEnVeille(boolean b, Pageable paging);
}
