package com.housservice.housstock.service;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface PlanificationService {


     void updatePlanfication(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> getPlanificationByIdLigneCmdAndIndex(String idLc, int index);

    List<PlanificationOfDTO> getPlanificationByIdLigneCmd(String idLc) throws ResourceNotFoundException;
    List<PlanificationOfDTO> getAllPlanificationsParOperation(String operationType) throws ResourceNotFoundException;
    List<PlanificationOfDTO> getAllPlanificationsParMachine(String operationType) throws ResourceNotFoundException;


}
