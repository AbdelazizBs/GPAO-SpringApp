package com.housservice.housstock.repository;

import com.housservice.housstock.model.Comptes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Personnel;

import java.util.List;

public interface PersonnelRepository extends MongoRepository<Personnel, String> {
Personnel findByNom(String s);
    Personnel findByCompte(Comptes comptes);
    Page<Personnel> findPersonnelByMiseEnVeille(boolean b, Pageable pageable);

}