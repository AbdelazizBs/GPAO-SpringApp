package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.housservice.housstock.model.Fournisseur;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface FournisseurRepository extends MongoRepository<Fournisseur, String>{
	
	Optional<Fournisseur> findByIntitule(String intitule);
	
	 boolean existsFournisseurByRefFrsIris(String refFrsIris);
	 
	  Page<Fournisseur> findFournisseurByMiseEnVeille(boolean b, Pageable pageable);
	  
	  @Query( "{$or:[{'refFrsIris': {$regex : ?0}} ,{'intitule': {$regex : ?0}} ,{'abrege': {$regex : ?0}},{'ville': {$regex : ?0}},{'pays': {$regex : ?0}}] }")
	  Page<Fournisseur> findFournisseurByTextToFindAndMiseEnVeille(String textToFind,boolean b, Pageable pageable);


}
