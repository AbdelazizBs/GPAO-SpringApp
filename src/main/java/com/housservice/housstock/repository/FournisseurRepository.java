package com.housservice.housstock.repository;

import com.housservice.housstock.model.Fournisseur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface FournisseurRepository extends MongoRepository<Fournisseur, String>{
	
	    Optional<Fournisseur> findByIntitule(String intitule);
	    
	    Optional<Fournisseur> findByRefFrsIris(String refFrsIris);
	    
	    boolean existsFournisseurByRefFrsIris(String refFrsIris);
	        
	    boolean existsFournisseurByRefFrsIrisAndIntitule(String refFrsIris,String intitule);
	      
	    Page<Fournisseur> findFournisseurByMiseEnVeille(boolean b, Pageable pageable);

	    @Query( "{$or:[{'refFrsIris': {$regex : ?0}} ,{'intitule': {$regex : ?0}} ,{'abrege': {$regex : ?0}},{'ville': {$regex : ?0}},{'interlocuteur': {$regex : ?0}},{'telephone': {$regex : ?0}},{'pays': {$regex : ?0}}] }")
		  Page<Fournisseur> findFournisseurByTextToFindAndMiseEnVeille(String textToFind,boolean b, Pageable pageable);
	

}
