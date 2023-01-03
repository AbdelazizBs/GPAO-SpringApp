package com.housservice.housstock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.housservice.housstock.model.Matiere;


public interface MatiereRepository extends MongoRepository<Matiere, String>{
	
	    Optional<Matiere> findByRefMatiereIris(String refMatiereIris);   
	    Optional<Matiere> findByDesignation(String designation);
	    
	    
	    boolean existsMatiereByRefMatiereIris(String refMatiereIris);
	    
	    boolean existsMatiereByRefMatiereIrisAndDesignation(String refMatiereIris,String designation);
	    
	
	    Page<Matiere> findMatiereByMiseEnVeille(boolean b, Pageable pageable);


	    @Query( "{$or:[{'refMatiereIris': {$regex : ?0}} ,{'designation': {$regex : ?0}} ,{'famille': {$regex : ?0}},{'uniteAchat': {$regex : ?0}},{'prixUnitaire': {$regex : ?0}}] }")
	    Page<Matiere> findMatiereByTextToFindAndMiseEnVeille(String textToFind,boolean b, Pageable pageable);

}
