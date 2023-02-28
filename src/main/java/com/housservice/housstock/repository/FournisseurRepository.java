package com.housservice.housstock.repository;

import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface FournisseurRepository extends MongoRepository <Fournisseur, String> {

		Page<Fournisseur> findFournisseurByMiseEnVeille(Pageable pageable, boolean b);


		Optional<Fournisseur> findFournisseurByContactId(String idContact ) ;
		Optional<Fournisseur> findFournisseurByPictures(Picture picture) ;

		boolean existsFournisseurByRefFrsIris(String matricule);
		boolean existsFournisseurByintitule(String matricule);
		Optional<Fournisseur> findFournisseurByintitule(String intitule);

	@Query( "{$or:[{'refFrsIris': {$regex : ?0}},{'intitule': {$regex : ?0}} ,{'adresse': {$regex : ?0}}] }")
	Page<Fournisseur> findFournisseurByTextToFind(String textToFind,  Pageable pageable);




}