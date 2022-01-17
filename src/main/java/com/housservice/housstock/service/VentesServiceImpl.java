package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Ventes;
import com.housservice.housstock.model.dto.VentesDto;
import com.housservice.housstock.repository.VentesRepository;

@Service
public class VentesServiceImpl implements VentesService {
	
	private VentesRepository ventesRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;

	@Autowired
	public VentesServiceImpl(VentesRepository ventesRepository, SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties) 
	{
		this.ventesRepository = ventesRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
	
	
	@Override
	public VentesDto buildVentesDtoFromVentes(Ventes ventes) {
		if (ventes == null)
		{
			return null;
		}
			
		VentesDto ventesDto = new VentesDto();
		ventesDto.setId(ventes.getId());
		ventesDto.setCode(ventes.getCode());
		ventesDto.setDateVente(ventes.getDateVente());
		ventesDto.setCommentaire(ventes.getCommentaire());

		return ventesDto;
		
	}

	
	private Ventes buildVentesFromVentesDto(VentesDto ventesDto) {
		
		Ventes ventes = new Ventes();
		ventes.setId(""+sequenceGeneratorService.generateSequence(Ventes.SEQUENCE_NAME));	
		ventes.setCode(ventesDto.getCode());
		ventes.setDateVente(ventesDto.getDateVente());
		ventes.setCommentaire(ventesDto.getCommentaire());

		return ventes;
		
	}
	
	
	@Override
	public List<VentesDto> getAllVentes() {
		
		List<Ventes> listVentes = ventesRepository.findAll();
		
		return listVentes.stream()
				.map(ventes -> buildVentesDtoFromVentes(ventes))
				.filter(ventes -> ventes != null)
				.collect(Collectors.toList());
	}

	@Override
	public VentesDto getVentesById(String id) {
		
	    Optional<Ventes> ventesOpt = ventesRepository.findById(id);
		if(ventesOpt.isPresent()) {
			return buildVentesDtoFromVentes(ventesOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewVentes(@Valid VentesDto ventesDto) {
	
		ventesRepository.save(buildVentesFromVentesDto(ventesDto));
		
	}

	@Override
	public void updateVentes(@Valid VentesDto ventesDto) throws ResourceNotFoundException {
		
		Ventes ventes = ventesRepository.findById(ventesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), ventesDto.getId())));
		
		ventes.setCode(ventesDto.getCode());
		ventes.setDateVente(ventesDto.getDateVente());
		ventes.setCommentaire(ventesDto.getCommentaire());

		ventesRepository.save(ventes);
	}

	@Override
	public void deleteVentes(String ventesId) {
		
		ventesRepository.deleteById(ventesId);

	}

}
