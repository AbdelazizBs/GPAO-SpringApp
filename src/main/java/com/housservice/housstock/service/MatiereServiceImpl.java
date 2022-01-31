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
import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;
import com.housservice.housstock.repository.MatiereRepository;

@Service
public class MatiereServiceImpl implements MatiereService {

	private MatiereRepository MatiereRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	
	@Autowired
	public MatiereServiceImpl (MatiereRepository MatiereRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.MatiereRepository = MatiereRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;

	}
	
	
	@Override
	public MatiereDto buildMatiereDtoFromMatiere(Matiere Matiere) {
		if (Matiere == null)
		{
			return null;
		}
			
		MatiereDto MatiereDto = new MatiereDto();
		MatiereDto.setId(Matiere.getId());
		MatiereDto.setCodeMatiere(Matiere.getCodeMatiere());
		MatiereDto.setDesignation(Matiere.getDesignation());
		MatiereDto.setPrixUnitaireHt(Matiere.getPrixUnitaireHt());
		MatiereDto.setTauxTva(Matiere.getTauxTva());
		MatiereDto.setPrixUnitaireTtc(Matiere.getPrixUnitaireTtc());
		MatiereDto.setPhoto(Matiere.getPhoto());
		
		return MatiereDto;
		
	}

	
	private Matiere buildMatiereFromMatiereDto(MatiereDto MatiereDto) {
		
		Matiere Matiere = new Matiere();
		Matiere.setId(""+sequenceGeneratorService.generateSequence(Matiere.SEQUENCE_NAME));	
		Matiere.setCodeMatiere(MatiereDto.getCodeMatiere());
		Matiere.setDesignation(MatiereDto.getDesignation());
		Matiere.setPrixUnitaireHt(MatiereDto.getPrixUnitaireHt());
		Matiere.setTauxTva(MatiereDto.getTauxTva());
		Matiere.setPrixUnitaireTtc(MatiereDto.getPrixUnitaireTtc());
		Matiere.setPhoto(MatiereDto.getPhoto());
		
		return Matiere;
		
	}
	
	
	@Override
	public List<MatiereDto> getAllMatiere() {
		
		List<Matiere> listMatiere = MatiereRepository.findAll();
		
		return listMatiere.stream()
				.map(Matiere -> buildMatiereDtoFromMatiere(Matiere))
				.filter(Matiere -> Matiere != null)
				.collect(Collectors.toList());
	}

	@Override
	public MatiereDto getMatiereById(String id) {
		
	    Optional<Matiere> MatiereOpt = MatiereRepository.findById(id);
		if(MatiereOpt.isPresent()) {
			return buildMatiereDtoFromMatiere(MatiereOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewMatiere(@Valid MatiereDto MatiereDto) {
	
		MatiereRepository.save(buildMatiereFromMatiereDto(MatiereDto));
		
	}

	@Override
	public void updateMatiere(@Valid MatiereDto MatiereDto) throws ResourceNotFoundException {
		
		Matiere Matiere = MatiereRepository.findById(MatiereDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), MatiereDto.getId())));
		
		Matiere.setCodeMatiere(MatiereDto.getCodeMatiere());
		Matiere.setDesignation(MatiereDto.getDesignation());
		Matiere.setPrixUnitaireHt(MatiereDto.getPrixUnitaireHt());
		Matiere.setTauxTva(MatiereDto.getTauxTva());
		Matiere.setPrixUnitaireTtc(MatiereDto.getPrixUnitaireTtc());
		Matiere.setPhoto(MatiereDto.getPhoto());
		
		MatiereRepository.save(Matiere);
	}

	@Override
	public void deleteMatiere(String MatiereId) {
		
		MatiereRepository.deleteById(MatiereId);

		
	}
	
}
