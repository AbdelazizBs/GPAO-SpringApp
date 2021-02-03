package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.UniteMesure;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface UniteMesureRepository extends MongoRepository<UniteMesure, Long> {

}
