package com.housservice.housstock.service;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;


public interface MachineService {

	 ResponseEntity<Map<String, Object>> getAllMachine(int page , int size);

	 ResponseEntity<Map<String, Object>>getMachineEnVeille( int page,int size);
	 String getIdMachine(String nomEtape) throws ResourceNotFoundException;
	 List<Machine> getAllMachinesByEtapes(String nomEtape) throws ResourceNotFoundException;

     MachineDto getMachineById(String id);

    public MachineDto buildMachineDtoFromMachine(Machine machine);

    public void createNewMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException;

     void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException;
    public void setEtatEnmarche(String idMachine) throws ResourceNotFoundException;
    public void setMachineEnVeille(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnPause(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnRepos(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnMaintenance(String idMachine) throws ResourceNotFoundException;
    public void setEtatEnPanne(String idMachine) throws ResourceNotFoundException;

    public void deleteMachine(String machineId);

}
