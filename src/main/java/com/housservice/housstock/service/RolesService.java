package com.housservice.housstock.service;

import java.util.List;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.RolesDto;

public interface RolesService {

	public List<RolesDto> getAllRoles();
	
    public RolesDto getRolesById(String id);
	
    public RolesDto buildRolesDtoFromRoles(Roles roles);

    public void createNewRoles(@Valid RolesDto rolesDto);
	
    public void updateRoles(@Valid RolesDto rolesDto) throws ResourceNotFoundException;
    
    public void deleteRoles(String rolesId);
}
