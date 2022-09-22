package com.housservice.housstock.service;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;


public interface MachineService {

	public List<MachineDto> findMachineActif();
	
	public List<MachineDto> findMachineNotActif();

    public MachineDto getMachineById(String id);
     
    Optional<Machine> getMachineParId(String id);
	
    public MachineDto buildMachineDtoFromMachine(Machine machine);

    public void createNewMachine(@Valid MachineDto machineDto);
	
    public void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException;

    public void deleteMachine(Machine machine);

}
