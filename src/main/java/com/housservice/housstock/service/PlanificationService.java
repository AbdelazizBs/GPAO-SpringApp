package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;

import javax.validation.Valid;

public interface PlanificationService {
    PlanificationOfDTO buildPlanificationOfDTOFromPlanificationOf(PlanificationOf planificationOf);

    PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;

    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;


}
