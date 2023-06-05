package com.housservice.housstock.repository;

import com.housservice.housstock.model.*;
import com.housservice.housstock.model.PlanEtapes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PlanEtapesRepository extends MongoRepository<PlanEtapes, String> {



    @Query( "{$or:[{'nomEtape': {$regex : ?0}} ,{'quantiteConforme': {$regex : ?0}} ,{'monitrice': {$regex : ?0}},{'ref': {$regex : ?0}}] }")
    Page<PlanEtapes> findPlanEtapesByTextToFind(String textToFind, Pageable paging);

    Page<PlanEtapes> findPlanEtapesByTerminer(boolean b, Pageable paging);

    List<PlanEtapes> findPlanEtapesByPersonnels(String nom);
}
