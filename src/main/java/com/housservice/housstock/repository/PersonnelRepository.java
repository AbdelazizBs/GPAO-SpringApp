package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonnelRepository extends MongoRepository<Personnel, String>{
    Optional<Personnel> findByNom(String s);
    Optional<Personnel> findByCompte(Comptes comptes);
    Optional<Personnel> findPersonnelByCin(String cin);
    Optional<Personnel> findPersonnelByMatricule(String matricule);
    boolean existsPersonnelByCin(String cin);
    boolean existsPersonnelByMatricule(String matricule);
    int countByCin(String cin);
    int countByMatricule(String matricule);
    Page<Personnel> findPersonnelByMiseEnVeille(boolean b, Pageable pageable);

    @Query( "{$or:[{'nom': {$regex : ?0}} ,{'prenom': {$regex : ?0}} ,{'matricule': {$regex : ?0}},{'poste': {$regex : ?0}},{'phone': {$regex : ?0}}] }")
    Page<Personnel> findPersonnelByTextToFind(String textToFind,Pageable pageable);

//    Page<Personnel> findAllByOOrderBy(Pageable pageable);

}