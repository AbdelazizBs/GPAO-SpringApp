package com.housservice.housstock.service;
import java.util.List;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;


public interface MachineService {

	public List<MachineDto> getAllMachine();
	
	public List<MachineDto> getMachineEnVeille();

    public MachineDto getMachineById(String id);
	
    public MachineDto buildMachineDtoFromMachine(Machine machine);

    public void createNewMachine(@Valid MachineDto machineDto);
	
    public void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException;
    public void setEtatEnmarche(String idMachine) throws ResourceNotFoundException;
    public void setMachineEnVeille(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnPause(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnRepos(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnMaintenance(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnPanne(String idMachine) throws ResourceNotFoundException;

    public void deleteMachine(String machineId);

}
