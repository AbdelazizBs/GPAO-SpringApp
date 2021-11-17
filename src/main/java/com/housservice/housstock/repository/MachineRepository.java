package com.housservice.housstock.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.housservice.housstock.model.Machine;

public interface MachineRepository extends MongoRepository<Machine, String> {

	  @Query("{ 'MiseEnVeille' : { $ne: 1}}")
	    List<Machine> findMachineActif();
	
	    @Query("{ 'MiseEnVeille' : 1}")
	    List<Machine> findMachineNotActif();
}