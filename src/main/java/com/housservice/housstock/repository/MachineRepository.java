package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import com.housservice.housstock.model.EtapeProduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Machine;

public interface MachineRepository extends MongoRepository<Machine, String> {

	Page<Machine> findByEnVeille(Boolean aBoolean,Pageable pageable) ;
	List<Machine> findMachineByEtapeProduction(EtapeProduction etapeProduction);
	Page<Machine> findAll(Pageable pageable);




}