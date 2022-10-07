package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.PlanificationOfDTO;

import javax.validation.Valid;
import java.util.List;

public interface PlanificationService {
    PlanificationOfDTO buildPlanificationOfDTOFromPlanificationOf(PlanificationOf planificationOf);

    PlanificationOf buildPlanificationOfFromPlanificationOfDTO(PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;
public List<PlanificationOfDTO> getPlanificationByIdLc(String id);

    public void updatePlanfication(@Valid PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException;


}
