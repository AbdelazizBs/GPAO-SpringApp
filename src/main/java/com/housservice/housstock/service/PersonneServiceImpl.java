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
import com.housservice.housstock.model.Personne;
import com.housservice.housstock.model.dto.PersonneDto;
import com.housservice.housstock.repository.PersonneRepository;

@Service
public class PersonneServiceImpl implements PersonneService{
	
	private PersonneRepository personneRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	
	@Autowired
	public PersonneServiceImpl(PersonneRepository personneRepository,
			SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.personneRepository = personneRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}

	@Override
	public PersonneDto buildPersonneDtoFromPersonne(Personne personne) {
		if (personne == null)
		{
			return null;
		}
			
		PersonneDto personneDto = new PersonneDto();
		personneDto.setId(personne.getId());
		personneDto.setNom(personne.getNom());
		personneDto.setPrenom(personne.getPrenom());
		personneDto.setSexe(personne.getSexe());
		personneDto.setDateDeNaissance(personne.getDateDeNaissance());
		personneDto.setCin(personne.getCin());
		personneDto.setAdresse(personne.getAdresse());
		personneDto.setPhoto(personne.getPhoto());
		personneDto.setRib(personne.getRib());
		
		return personneDto;
		
	}
	
	private Personne buildPersonneFromPersonneDto(PersonneDto personneDto)
	{
		Personne personne = new Personne();
		
		personne.setId(""+sequenceGeneratorService.generateSequence(Personne.SEQUENCE_NAME));	
		personne.setId(personneDto.getId());		
		personne.setNom(personneDto.getNom());
		personne.setPrenom(personneDto.getPrenom());
		personne.setSexe(personneDto.getSexe());
		personne.setDateDeNaissance(personneDto.getDateDeNaissance());
		personne.setCin(personneDto.getCin());
		personne.setAdresse(personneDto.getAdresse());
		personne.setPhoto(personneDto.getPhoto());
		personne.setRib(personneDto.getRib());

		return personne;
	}


	@Override
	public List<PersonneDto> getAllPersonne() {
		
	List<Personne> listPersonne = personneRepository.findAll();
		
		return listPersonne.stream()
				.map(personne -> buildPersonneDtoFromPersonne(personne))
				.filter(personne -> personne != null)
				.collect(Collectors.toList());
	}

	@Override
	public PersonneDto getPersonneById(String id) {
		
		 Optional<Personne> personneOpt = personneRepository.findById(id);
			if(personneOpt.isPresent()) {
				return buildPersonneDtoFromPersonne(personneOpt.get());
			}
			return null;
	}


	@Override
	public void createNewPersonne(@Valid PersonneDto personneDto) {
		
		personneRepository.save(buildPersonneFromPersonneDto(personneDto));
		
	}


	@Override
	public void updatePersonne(@Valid PersonneDto personneDto) throws ResourceNotFoundException {
		
		Personne personne = personneRepository.findById(personneDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personneDto.getId())));
		
		personne.setNom(personneDto.getNom());
		personne.setPrenom(personneDto.getPrenom());
		personne.setSexe(personneDto.getSexe());
		personne.setDateDeNaissance(personneDto.getDateDeNaissance());
		personne.setCin(personneDto.getCin());
		personne.setAdresse(personneDto.getAdresse());
		personne.setPhoto(personneDto.getPhoto());
		personne.setRib(personneDto.getRib());
		
		personneRepository.save(personne);
		
	}


	@Override
	public void deletePersonne(String personneId) {
		
		personneRepository.deleteById(personneId);
		
	}


}
