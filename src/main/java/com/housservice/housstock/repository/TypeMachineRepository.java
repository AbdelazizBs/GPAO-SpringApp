package com.housservice.housstock.repository;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.TypeMachine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeMachineRepository extends MongoRepository<TypeMachine, String> {

    TypeMachine deleteByNom(String autre);

    boolean existsTypeMachineByNom(String nom);
    String findByNom(String nom);
}

