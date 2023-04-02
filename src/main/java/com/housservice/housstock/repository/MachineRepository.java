package com.housservice.housstock.repository;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MachineRepository extends MongoRepository<Machine, String> {
    boolean existsMachineByRefMachine(String refMachine);
    Page<Machine> findMachineByTextToFind(String textToFind, Pageable pageable);
}
