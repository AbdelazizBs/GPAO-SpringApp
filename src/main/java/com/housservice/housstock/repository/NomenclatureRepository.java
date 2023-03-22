package com.housservice.housstock.repository;

import com.housservice.housstock.model.Nomenclature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {

    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
    Page<Nomenclature> findNomenclatureActif(Pageable pageable);


    boolean existsNomenclatureByNomNomenclature(String nomNomenclature);

    Optional<Nomenclature> findNomenclatureByNomNomenclature(String nomNomenclature);

    List<Nomenclature> findNomenclatureByMiseEnVeille(boolean miseEnVeille);

    List<Nomenclature> findNomenclatureByMiseEnVeilleAndType(boolean miseEnVeille, String type);

    @Query("{$or:[{'nomNomenclature': {$regex : ?0}},{'type': {$regex : ?0}} ,{'nature': {$regex : ?0}} ,{'categorie': {$regex : ?0}}] }")
    Page<Nomenclature> findNomenclatureByTextToFindAndMiseEnVeille(String textToFind, boolean b, Pageable pageable);

    List<Nomenclature> findNomenclatureByClientId(String clientId);

    List<Nomenclature> findNomenclatureByFournisseurId(String frsId);

    List<Nomenclature> findNomenclaturesByClientId(String idClient);
}
