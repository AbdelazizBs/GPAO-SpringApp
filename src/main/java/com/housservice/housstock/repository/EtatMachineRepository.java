package com.housservice.housstock.repository;

import com.housservice.housstock.model.EtatMachine;
import com.housservice.housstock.model.dto.EtatMachineDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EtatMachineRepository extends MongoRepository<EtatMachine, String> {
  Optional<EtatMachine>  findEtatMachineByIdMachine(String s);


Optional<EtatMachine>   findEtatMachineByDateDebut(LocalDate localDate);
}
