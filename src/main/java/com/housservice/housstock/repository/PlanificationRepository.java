package com.housservice.housstock.repository;

import com.housservice.housstock.model.PlanificationOf;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanificationRepository extends MongoRepository<PlanificationOf,String > {
}
