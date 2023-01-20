package com.housservice.housstock.repository;

import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {
	
    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
	Page<Nomenclature> findNomenclatureActif(Pageable pageable);

    @Query("{ 'MiseEnVeille' : 1}")
    Page<Nomenclature> findNomenclatureNotActif(Pageable pageable);

	Optional<Nomenclature> findNomenclatureByPictures(Picture picture) ;

	boolean existsNomenclatureByNomNomenclature(String nomNomenclature);
	
	Optional<Nomenclature> findNomenclatureByNomNomenclature(String nomNomenclature);
	List<Nomenclature> findNomenclatureByTypeAndMiseEnVeille(String type, int miseEnVeille);
	List<Nomenclature> findNomenclatureByMiseEnVeille( int miseEnVeille);
//Nomenclature findNomenclatureById(String id);

@Query( "{$or:[{'nomNomenclature': {$regex : ?0}},{'type': {$regex : ?0}} ,{'nature': {$regex : ?0}} ,{'categorie': {$regex : ?0}}] }")
Page<Nomenclature> findNomenclatureByTextToFindAndMiseEnVeille(String textToFind,boolean b ,  Pageable pageable);
}
