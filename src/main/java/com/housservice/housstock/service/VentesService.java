package com.housservice.housstock.service;

import java.util.List;
import javax.validation.Valid;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Ventes;
import com.housservice.housstock.model.dto.VentesDto;

public interface VentesService {

	public List<VentesDto> getAllVentes();
	
    public VentesDto getVentesById(String id);
	
    public VentesDto buildVentesDtoFromVentes(Ventes ventes);

    public void createNewVentes(@Valid VentesDto ventesDto);
	
    public void updateVentes(@Valid VentesDto ventesDto) throws ResourceNotFoundException;
    
    public void deleteVentes(String ventesId);
}
