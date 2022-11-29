package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonnelRepository extends MongoRepository<Personnel, String>{
    Optional<Personnel> findByNom(String s);
    Optional<Personnel> findByCompte(Comptes comptes);
    List<Personnel> findPersonnelByMatricule(String matricule);
    List<Personnel> findPersonnelByCin(String cin);
    Page<Personnel> findPersonnelByMiseEnVeille(boolean b, Pageable pageable);

    @Query( "{$or:[{'nom': {$regex : ?0}} ,{'prenom': {$regex : ?0}} ,{'matricule': {$regex : ?0}},{'poste': {$regex : ?0}},{'phone': {$regex : ?0}}] }")
    Page<Personnel> findPersonnelByTextToFindAndMiseEnVeille(String textToFind,boolean b, Pageable pageable);





}