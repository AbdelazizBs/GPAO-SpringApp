package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AtelierService {
    Optional<Machine> getMachineById(String id);
    ResponseEntity<Map<String, Object>> getOfByRefMachine(int page, int size);
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille);
    void updateEtapes(String id, PlanEtapesDto planEtapesDto) throws ResourceNotFoundException;
    void terminer(String id,PlanEtapesDto planEtapesDto) throws ResourceNotFoundException;
     List<String> getMonitrice();
     ResponseEntity<Map<String, Object>> onSortActiveAtelier(int page, int size, String field, String order);

}
