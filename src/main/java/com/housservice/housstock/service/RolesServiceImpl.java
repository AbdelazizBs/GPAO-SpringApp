package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.Utilisateur;
import com.housservice.housstock.model.dto.RolesDto;
import com.housservice.housstock.repository.RolesRepository;
import com.housservice.housstock.repository.UtilisateurRepository;

@Service
public class RolesServiceImpl implements RolesService {
	
	private RolesRepository rolesRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	public RolesServiceImpl(RolesRepository rolesRepository, SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties, UtilisateurRepository utilisateurRepository)
	{
		this.rolesRepository = rolesRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.utilisateurRepository = utilisateurRepository;
	}

	@Override
	public RolesDto buildRolesDtoFromRoles(Roles roles) {
		if (roles == null)
		{
			return null;
		}
			
		RolesDto rolesDto = new RolesDto();
		rolesDto.setId(roles.getId());
		rolesDto.setNom(roles.getNom());

//		rolesDto.setIdUtilisateur(roles.getUtilisateur().getId());
//		rolesDto.setNomUtilisateur(roles.getUtilisateur().getNom());
		
		return rolesDto;
		
	}
	
	private Roles buildRolesFromRolesDto(RolesDto rolesDto)
	{
		Roles roles = new Roles();
		
		roles.setId(""+sequenceGeneratorService.generateSequence(Roles.SEQUENCE_NAME));	
		roles.setId(rolesDto.getId());		
		roles.setNom(rolesDto.getNom());
//
//		Utilisateur ut = utilisateurRepository.findById(rolesDto.getIdUtilisateur()).get();
//		roles.setUtilisateur(ut);
		
		return roles;
	}


	@Override
	public List<RolesDto> getAllRoles() {
		
	List<Roles> listRoles = rolesRepository.findAll();
		
		return listRoles.stream()
				.map(roles -> buildRolesDtoFromRoles(roles))
				.filter(roles -> roles != null)
				.collect(Collectors.toList());
	}

	@Override
	public RolesDto getRolesById(String id) {
		
		 Optional<Roles> rolesOpt = rolesRepository.findById(id);
			if(rolesOpt.isPresent()) {
				return buildRolesDtoFromRoles(rolesOpt.get());
			}
			return null;
	}


	@Override
	public void createNewRoles(@Valid RolesDto rolesDto) {
		
		rolesRepository.save(buildRolesFromRolesDto(rolesDto));
		
	}


	@Override
	public void updateRoles(@Valid RolesDto rolesDto) throws ResourceNotFoundException {
		
		Roles roles = rolesRepository.findById(rolesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), rolesDto.getId())));
		
		roles.setNom(rolesDto.getNom());
	
//		  if(roles.getUtilisateur() == null ||!StringUtils.equals(rolesDto.getIdUtilisateur(),roles.getUtilisateur().getId()))
//		  {
//			  Utilisateur ut = utilisateurRepository.findById(rolesDto.getIdUtilisateur()).get();
//		      roles.setUtilisateur(ut); }
		 
		rolesRepository.save(roles);
		
	}


	@Override
	public void deleteRoles(String rolesId) {
		
		rolesRepository.deleteById(rolesId);
		
	}

}
