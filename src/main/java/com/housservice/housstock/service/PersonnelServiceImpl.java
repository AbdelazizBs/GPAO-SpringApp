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
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.PersonnelRepository;

@Service
public class PersonnelServiceImpl implements PersonnelService{
	
	private PersonnelRepository personnelRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	
	@Autowired
	public PersonnelServiceImpl(PersonnelRepository personnelRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties)
{
		this.personnelRepository = personnelRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}

	@Override
	public PersonnelDto buildPersonnelDtoFromPersonnel(Personnel personnel) {
		if (personnel == null)
		{
			return null;
		}
			
		PersonnelDto personnelDto = new PersonnelDto();
		personnelDto.setId(personnel.getId());
		personnelDto.setCin(personnel.getCin());
		personnelDto.setNom(personnel.getNom());
		personnelDto.setPrenom(personnel.getPrenom());
		personnelDto.setSexe(personnel.getSexe());
		personnelDto.setDateDeNaissance(personnel.getDateDeNaissance());
		personnelDto.setAdresse(personnel.getAdresse());
		personnelDto.setRib(personnel.getRib());
		personnelDto.setPoste(personnel.getPoste());
		personnelDto.setDateDeEmbauche(personnel.getDateDeEmbauche());
		personnelDto.setEchelon(personnel.getEchelon());
		personnelDto.setCategorie(personnel.getCategorie());
		
		
		return personnelDto;
		
	}
	
	private Personnel buildPersonnelFromPersonnelDto(PersonnelDto personnelDto)
	{
		Personnel personnel = new Personnel();
		
		personnel.setId(""+sequenceGeneratorService.generateSequence(Personnel.SEQUENCE_NAME));	
		personnel.setId(personnelDto.getId());
		personnel.setCin(personnelDto.getCin());
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setDateDeNaissance(personnelDto.getDateDeNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateDeEmbauche(personnelDto.getDateDeEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
				
		return personnel;
	}


	@Override
	public List<PersonnelDto> getAllPersonnel() {
		
	List<Personnel> listPersonnel = personnelRepository.findAll();
		
		return listPersonnel.stream()
				.map(personnel -> buildPersonnelDtoFromPersonnel(personnel))
				.filter(personnel -> personnel != null)
				.collect(Collectors.toList());
	}

	@Override
	public PersonnelDto getPersonnelById(String id) {
		
		 Optional<Personnel> personnelOpt = personnelRepository.findById(id);
			if(personnelOpt.isPresent()) {
				return buildPersonnelDtoFromPersonnel(personnelOpt.get());
			}
			return null;
	}


	@Override
	public void createNewPersonnel(@Valid PersonnelDto personnelDto) {
		
		personnelRepository.save(buildPersonnelFromPersonnelDto(personnelDto));
		
	}


	@Override
	public void updatePersonnel(@Valid PersonnelDto personnelDto) throws ResourceNotFoundException {
		
		Personnel personnel = personnelRepository.findById(personnelDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
		
		personnel.setCin(personnelDto.getCin());
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setDateDeNaissance(personnelDto.getDateDeNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateDeEmbauche(personnelDto.getDateDeEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
	
		personnelRepository.save(personnel);
		
	}


	@Override
	public void deletePersonnel(String personnelId) {
		
		personnelRepository.deleteById(personnelId);
		
	}

}
