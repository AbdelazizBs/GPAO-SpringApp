package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Plannification;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PlannificationService {

    ResponseEntity<Map<String, Object>> getAllArticle(int page, int size);
    void updatePlanification(String id, Plannification plannification) throws ResourceNotFoundException;

}
