package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import com.housservice.housstock.model.EtapeProduction;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Machine;

public interface MachineRepository extends MongoRepository<Machine, String> {

	List<Machine> findByEnVeille(Boolean aBoolean) ;
	List<Machine> findMachineByEtapeProduction(EtapeProduction etapeProduction);



}