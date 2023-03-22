package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;

import javax.validation.Valid;
import java.util.List;

public interface PlanificationService {
    PlanificationOfDTO buildPlanificationOfDTOFromPlanificationOf(PlanificationOf planificationOf);

    PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    List<PlanificationOf> getPlanificationEtape(String idLc) throws ResourceNotFoundException;

    PlanificationOf getPlanificationByIdLigneCmdAndNamEtape(String idLc, String nomEtape) throws ResourceNotFoundException;

}
