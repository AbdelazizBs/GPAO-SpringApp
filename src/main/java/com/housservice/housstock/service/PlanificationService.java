package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface PlanificationService {

    PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> getPlanificationByIdLigneCmdAndIndex(String idLc, int index);

    List<PlanificationOf> getPlanificationByIdLigneCmd(String idLc) throws ResourceNotFoundException;

}
