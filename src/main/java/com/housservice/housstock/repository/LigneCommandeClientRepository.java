package com.housservice.housstock.repository;

import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.LigneCommandeClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends MongoRepository<LigneCommandeClient, String>{
	


	    List<LigneCommandeClient> findLigneCommandeClientByCommandeClient(CommandeClient commandeClient) ;


}
