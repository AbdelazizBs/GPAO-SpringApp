package com.housservice.housstock.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.MetaData;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface MetaDataRepository extends MongoRepository<MetaData, Long> {

	List<MetaData> findByCatalogue(String catalogue);
}
