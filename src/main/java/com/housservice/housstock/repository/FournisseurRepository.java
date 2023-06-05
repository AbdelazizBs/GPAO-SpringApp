package com.housservice.housstock.repository;

import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface FournisseurRepository extends MongoRepository <Fournisseur, String> {

	Page<Fournisseur> findFournisseurByMiseEnVeille(Pageable pageable, boolean b);

	List<Fournisseur> findFournisseurByMiseEnVeille(boolean b);

	Optional<Fournisseur> findFournisseurByContactId(String idContact ) ;
	Optional<Fournisseur> findFournisseurByPictures(Picture picture) ;

	boolean existsFournisseurByRefFournisseurIris(String matricule);
	boolean existsFournisseurByRaisonSocial(String matricule);
	Optional<Fournisseur> findFournisseurByRaisonSocial(String raisonSocial);

	@Query( "{$or:[{'refFournisseurIris': {$regex : ?0}},{'raisonSocial': {$regex : ?0}} ,{'abrege': {$regex : ?0}} ,{'linkedin': {$regex : ?0}},{'adresse': {$regex : ?0}}] }")
	Page<Fournisseur> findFournisseurByTextToFind(String textToFind,  Pageable pageable);


	List<Fournisseur> findByrefFournisseurIris(String id);

	List<Fournisseur> findBydateBetween(Date fromDate , Date toDate);

    List<Fournisseur> findFournisseurById(String id);

    List<Fournisseur> findFournisseurByPays(String pays);
}