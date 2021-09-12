package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Nomenclature;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {

	Optional<List<Nomenclature>> findByIdCompteAndTypeAndIdParent(String idCompte, String typeFamille, String string);
	Optional<List<Nomenclature>> findByNomLikeOrDescriptionLikeAndTypeAllIgnoreCase(String nom, String descriptio, String type);
}
