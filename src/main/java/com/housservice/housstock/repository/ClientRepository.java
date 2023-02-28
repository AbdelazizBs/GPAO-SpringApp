package com.housservice.housstock.repository;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends MongoRepository <Client, String> {

		Page<Client> findClientByMiseEnVeille(Pageable pageable, boolean b);


		Optional<Client> findClientByContactId(String idContact ) ;
		Optional<Client> findClientByPictures(Picture picture) ;

		boolean existsClientByRefClientIris(String matricule);
		boolean existsClientByRaisonSocial(String matricule);
		Optional<Client> findClientByRaisonSocial(String raisonSocial);

	@Query( "{$or:[{'refClientIris': {$regex : ?0}},{'raisonSocial': {$regex : ?0}} ,{'secteurActivite': {$regex : ?0}} ,{'brancheActivite': {$regex : ?0}},{'adresse': {$regex : ?0}}] }")
	Page<Client> findClientByTextToFind(String textToFind,  Pageable pageable);


    List<Client> findByrefClientIris(String id);
}