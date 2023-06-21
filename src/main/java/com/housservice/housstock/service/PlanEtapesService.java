package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlanEtapesService {
    ResponseEntity<Map<String, Object>> getPlantoday(int page, int size,String personnels);
     ResponseEntity<Map<String, Object>> searchP(String textToFind, int page, int size, boolean enVeille);
     ResponseEntity<Map<String, Object>> onSortActivePlanEtapes(int page, int size, String field, String order);
    public void delete(String id) ;
}
