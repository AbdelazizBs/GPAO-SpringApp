package com.housservice.housstock.service;

import java.util.List;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import java.util.Optional;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;

public interface MachineService {

	public List<Machine> findMachineActif();
	
	public List<Machine> findMachineNotActif();

    Optional<Machine> getMachineById(String id);
	
	MachineDto buildMachineDtoFromMachine(Machine machine);
	
	void deleteMachine(Machine machine);
	
	void createNewMachine(@Valid MachineDto machineDto);
	
	void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException;

}
