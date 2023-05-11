package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PlannificationService {

    ResponseEntity<Map<String, Object>> getAllArticle(int page, int size);
    ResponseEntity<Map<String, Object>> getAllArticleLance(int page, int size);

    void updatePlanification(String id, Plannification plannification) throws ResourceNotFoundException;
    void updateEtapes(String id, PlanEtapesDto planEtapesDto) throws ResourceNotFoundException;

    String operationType(String etat);
    List<String> getConducteur(String refMachine);

    public String[] getEtape(String id);
    List<String>getMonitrice();
   void updateEtape(String id, Plannification plannification)throws ResourceNotFoundException;
   int indiceEtape(String id);
    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,boolean enVeille);
    PlanEtapes getEtapesValue(String id,String Nom);
     void Terminer(String id);
    void Suivi(String id);

    }
