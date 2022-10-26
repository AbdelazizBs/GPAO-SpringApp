package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.housservice.housstock.model.Client;

public interface ClientRepository extends MongoRepository <Client, String> {

	    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
	    List<Client> findClientActif();
	
	    @Query("{ 'MiseEnVeille' : 1}")
	    List<Client> findClientNotActif();

		Optional<Client> findClientByRaisonSocial(String raisonSociale) ;
		Optional<Client> findClientByContactId(String idContact ) ;

}
