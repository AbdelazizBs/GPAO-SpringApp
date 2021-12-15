package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.MvtStk;
import com.housservice.housstock.model.dto.MvtStkDto;

public interface MvtStkService {
	
	public List<MvtStkDto> getAllMvtStk();
	
    public MvtStkDto getMvtStkById(String id);
	
    public MvtStkDto buildMvtStkDtoFromMvtStk(MvtStk mvtStk);

    public void createNewMvtStk(@Valid MvtStkDto mvtStkDto);
	
    public void updateMvtStk(@Valid MvtStkDto mvtStkDto) throws ResourceNotFoundException;
    
    public void deleteMvtStk(String mvtStkId);

}
