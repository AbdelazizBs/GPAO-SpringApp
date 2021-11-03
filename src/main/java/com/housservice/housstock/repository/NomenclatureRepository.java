package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Nomenclature;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
public interface NomenclatureRepository extends MongoRepository<Nomenclature, String> {

	Optional<List<Nomenclature>> findByIdCompteAndTypeAndIdParent(String idCompte, String typeFamille, String idParent);
	Optional<List<Nomenclature>> findByIdCompteAndTypeAndIdParent(String idCompte, String typeFamille, String idParent, Sort sort);
	Optional<List<Nomenclature>> findByIdCompteAndNomLikeOrDescriptionLikeAndTypeAllIgnoreCase(String idCompt, String nom, String descriptio, String type);
	Optional<List<Nomenclature>> findByIdCompteAndIdParent(String idCompte, String idParent);
	Optional<List<Nomenclature>> findByIdCompteAndIdParent(String idCompte, String idParent, Sort sort);
}
