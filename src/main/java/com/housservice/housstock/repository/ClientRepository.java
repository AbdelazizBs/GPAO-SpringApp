package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.housservice.housstock.model.Client;

public interface ClientRepository extends MongoRepository <Client, String> {

	    @Query("{ 'MiseEnVeille' : { $ne: 1}}")
		Page<Client> findClientActif(Pageable pageable);
	
	    @Query("{ 'MiseEnVeille' : 1}")
	    Page<Client> findClientNotActif(Pageable pageable);

		Optional<Client> findClientByRaisonSocial(String raisonSociale) ;
		Optional<Client> findClientByContactId(String idContact ) ;

}
