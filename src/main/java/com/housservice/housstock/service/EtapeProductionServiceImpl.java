package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EtapeProductionDto;
import com.housservice.housstock.repository.EtapeProductionRepository;



@Service
public class EtapeProductionServiceImpl implements EtapeProductionService {

	private EtapeProductionRepository etapeProductionRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	

	@Autowired
	public EtapeProductionServiceImpl(EtapeProductionRepository etapeProductionRepository, SequenceGeneratorService sequenceGeneratorService,
									MessageHttpErrorProperties messageHttpErrorProperties) {
		this.etapeProductionRepository = etapeProductionRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
	
	
	@Override
	public EtapeProductionDto buildEtapeProductionDtoFromEtapeProduction(EtapeProduction etapeProduction) {
		if (etapeProduction == null) {
			return null;
		}
		
		EtapeProductionDto etapeProductionDto = new EtapeProductionDto();
		etapeProductionDto.setId(etapeProduction.getId());
		
		etapeProductionDto.setNom_etape(etapeProduction.getNom_etape());		
		etapeProductionDto.setType_etape(etapeProduction.getType_etape());
		
		return etapeProductionDto;
	}

	
	@Override
	public Optional<EtapeProduction> getEtapeProductionById(String etapeProductionId) {
		return etapeProductionRepository.findById(etapeProductionId);
	}

	
	@Override
	public void deleteEtapeProduction(EtapeProduction etapeProduction) {
		etapeProductionRepository.delete(etapeProduction);
		
	}

	@Override
	public void createNewEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) {
		
		etapeProductionRepository.save(buildEtapeProductionFromEtapeProductionDto(etapeProductionDto));
	}
	
	
	private EtapeProduction buildEtapeProductionFromEtapeProductionDto(EtapeProductionDto etapeProductionDto) {
		EtapeProduction etapeProduction = new EtapeProduction();
		etapeProduction.setId(""+sequenceGeneratorService.generateSequence(EtapeProduction.SEQUENCE_NAME));
		etapeProduction.setNom_etape(etapeProductionDto.getNom_etape());		
		etapeProduction.setType_etape(etapeProductionDto.getType_etape());
		
		return etapeProduction;
	
		}


	@Override
	public void updateEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) throws ResourceNotFoundException {
		
		EtapeProduction etapeProduction = getEtapeProductionById(etapeProductionDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(),  etapeProductionDto.getId())));
		
		etapeProduction.setNom_etape(etapeProductionDto.getNom_etape());		
		etapeProduction.setType_etape(etapeProductionDto.getType_etape());

		etapeProductionRepository.save(etapeProduction);
		
	}


	@Override
	public List<EtapeProduction> getAllEtapeProduction() {
		return etapeProductionRepository.findAll();
	}





}
