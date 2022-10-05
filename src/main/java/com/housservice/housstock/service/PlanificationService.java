package com.housservice.housstock.service;

import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;

public interface PlanificationService {
    PlanificationOfDTO buildPersonnelDtoFromPlanificationOf(PlanificationOf planificationOf);
}
