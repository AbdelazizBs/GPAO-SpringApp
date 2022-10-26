package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import com.housservice.housstock.model.DonneurOrdre;

public interface DonneurOrdreRepository extends MongoRepository<DonneurOrdre, String>{
	
	@Query("{'MiseEnVeille' : {$ne: 1}}")
	List<DonneurOrdre> findDonneurOrdreActif();
	
	@Query("{'MiseEnVeille' : 1}")
	List<DonneurOrdre> findDonneurOrdreNotActif();
	
	Optional<DonneurOrdre> findDonneurOrdreByRaisonSocial(String raisonSociale);


}
