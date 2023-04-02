package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.model.Machine;
import org.springframework.http.ResponseEntity;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MachineService {
    public ResponseEntity<Map<String, Object>> onSortActiveMachine(int page, int size, String field, String order);
    public ResponseEntity<Map<String, Object>> onSortMachineNotActive(int page, int size, String field, String order);
    void createNewMachine(String refMachine,
                          String nomConducteur, String libelle,
                          int nbConducteur, Date dateMaintenance,
                          String type
                          ) throws ResourceNotFoundException;

   // public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
    public void updateMachine(String idMachine ,String refMachine,
                              String nomConducteur, String libelle,
                              int nbConducteur, Date dateMaintenance,
                              String type) throws ResourceNotFoundException;
    void miseEnVeille(String idMachine ) throws ResourceNotFoundException;
    void deleteMachine(Machine machine);
    Optional<Machine> getMachineById(String id);

    List<String> getType();
}
