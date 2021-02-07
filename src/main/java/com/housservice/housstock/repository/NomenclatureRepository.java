package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Nomenclature;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {

}
