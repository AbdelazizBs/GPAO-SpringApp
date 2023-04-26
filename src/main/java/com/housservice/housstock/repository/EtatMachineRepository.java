package com.housservice.housstock.repository;

import com.housservice.housstock.model.EtatMachine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtatMachineRepository extends MongoRepository<EtatMachine, String> {
}
