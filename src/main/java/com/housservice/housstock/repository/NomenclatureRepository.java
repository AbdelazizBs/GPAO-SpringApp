package com.housservice.housstock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;

public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {
	
    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
	Page<Nomenclature> findNomenclatureActif(Pageable pageable);

    @Query("{ 'MiseEnVeille' : 1}")
    Page<Nomenclature> findNomenclatureNotActif(Pageable pageable);

	Optional<Nomenclature> findNomenclatureByPictures(Picture picture) ;

	boolean existsNomenclatureByNomNomenclature(String nomNomenclature);
	
	Optional<Nomenclature> findNomenclatureByNomNomenclature(String nomNomenclature);
	Optional<Nomenclature> findNomenclatureByType(String type);


@Query( "{$or:[{'nomNomenclature': {$regex : ?0}},{'type': {$regex : ?0}} ,{'nature': {$regex : ?0}} ,{'categorie': {$regex : ?0}}] }")
Page<Nomenclature> findNomenclatureByTextToFindAndMiseEnVeille(String textToFind,boolean b ,  Pageable pageable);
}
