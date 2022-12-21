package com.housservice.housstock.repository;

import com.housservice.housstock.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface ClientRepository extends MongoRepository <Client, String> {

	    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
		Page<Client> findClientActif(Pageable pageable);
	
	    @Query("{ 'MiseEnVeille' : 1}")
	    Page<Client> findClientNotActif(Pageable pageable);

		//Optional<Client> findClientByRaisonSocial(String raisonSociale) ;
		Optional<Client> findClientByContactId(String idContact ) ;
		
		boolean existsClientByRefClientIris(String matricule);
		Optional<Client> findClientByRaisonSocial(String raisonSocial);
		Optional<Client> findClientByRefClientIris(String raisonSocial);


	@Query( "{$or:[{'refClientIris': {$regex : ?0}},{'raisonSocial': {$regex : ?0}} ,{'secteurActivite': {$regex : ?0}} ,{'brancheActivite': {$regex : ?0}},{'adresse': {$regex : ?0}}] }")
	Page<Client> findClientByTextToFindAndMiseEnVeille(String textToFind,boolean b ,  Pageable pageable);

}