package com.housservice.housstock.service;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlanifierMachineService {
    Optional<Machine> getMachineById(String id);
    List<String> getMachineByNomConducteur(String nomConducteur);
    ResponseEntity<Map<String, Object>> getOfByRefMachine(int page, int size,String refMachine);
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille);
}
