package com.housservice.housstock.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Nomenclature;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {

	List<Nomenclature> findByTypeAndIdParent(String type, String idParent);
	List<Nomenclature> findByNomLikeOrLabelLikeOrDescriptionLikeAndTypeAllIgnoreCase(String nom, String label, String descriptio, String type);
}
