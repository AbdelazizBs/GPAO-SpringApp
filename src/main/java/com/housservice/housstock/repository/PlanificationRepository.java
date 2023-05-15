package com.housservice.housstock.repository;

import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.PlanificationOf;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlanificationRepository extends MongoRepository<PlanificationOf, String> {


List<PlanificationOf> findPlanificationOfByLanced(boolean lanced);
}
