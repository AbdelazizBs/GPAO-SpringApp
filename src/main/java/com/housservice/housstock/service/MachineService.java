package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import org.springframework.http.ResponseEntity;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MachineService {
    public ResponseEntity<Map<String, Object>> onSortActiveMachine(int page, int size, String field, String order);
    public ResponseEntity<Map<String, Object>> onSortMachineNotActive(int page, int size, String field, String order);
    void createNewMachine(MachineDto machineDto) throws ResourceNotFoundException;

    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
    public void updateMachine(MachineDto machineDto,String idmachine) throws ResourceNotFoundException;
    void miseEnVeille(String idMachine ) throws ResourceNotFoundException;
    void deleteMachine(Machine machine);
    void deleteMachineSelected(List<String> idMachinesSelected);

    Optional<Machine> getMachineById(String id);
    ResponseEntity<Map<String, Object>> getAllMachine(int page, int size);
    List<String> getAllMachineDisponible(String etape);
    ResponseEntity<Map<String, Object>> getAllMachineEnVielle(int page, int size);
    List<String> getType();
    public List<String> getConducteur();
    public void addType(String type) throws ResourceNotFoundException;
    public void Restaurer(String id) throws ResourceNotFoundException;
    public String getEtat(String id);

    public void reserve(String id);
    public void Demarer(String id);
    int getallMachine(String b);
    List<Integer> getClientListe(boolean b);
    List<Machine> getMachineListe();
}
