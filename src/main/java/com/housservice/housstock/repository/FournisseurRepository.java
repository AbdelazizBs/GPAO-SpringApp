package com.housservice.housstock.repository;

import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Picture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface FournisseurRepository extends MongoRepository<Fournisseur, String>{
	
			@Query("{ 'MiseEnVeille' : { $ne: 1}}")
			Page<Fournisseur> findFournisseurActif(Pageable pageable);
		
		   @Query("{ 'MiseEnVeille' : 1}")
		    Page<Fournisseur> findFournisseurNotActif(Pageable pageable);
	
			Optional<Fournisseur> findFournisseurByContactId(String idContact ) ;
			Optional<Fournisseur> findFournisseurByPictures(Picture picture) ;

			boolean existsFournisseurByRefFrsIris(String refFrsIris);
			
			boolean existsFournisseurByIntitule(String refFrsIris);
			
			Optional<Fournisseur> findFournisseurByIntitule(String intitule);
			
			Optional<Fournisseur> findFournisseurByRefFrsIris(String refFrsIris);

		 @Query( "{$or:[{'refFrsIris': {$regex : ?0}} ,{'intitule': {$regex : ?0}} ,{'abrege': {$regex : ?0}},{'ville': {$regex : ?0}},{'telephone': {$regex : ?0}},{'pays': {$regex : ?0}}] }")
		  Page<Fournisseur> findFournisseurByTextToFindAndMiseEnVeille(String textToFind,boolean b, Pageable pageable);
		   	

}
