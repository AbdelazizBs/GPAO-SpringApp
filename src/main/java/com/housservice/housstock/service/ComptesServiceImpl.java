package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.housservice.housstock.mapper.CompteMapper;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.RolesRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.dto.ComptesDto;
import com.housservice.housstock.repository.ComptesRepository;
import com.housservice.housstock.repository.EntrepriseRepository;
import com.housservice.housstock.repository.PersonnelRepository;

@Service
public class ComptesServiceImpl implements ComptesService , UserDetailsService {
	
	private ComptesRepository comptesRepository;
	private final PasswordEncoder passwordEncoder;

	private SequenceGeneratorService sequenceGeneratorService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	final
	RolesRepository rolesRepository;
	private EntrepriseRepository entrepriseRepository;
	
	private PersonnelRepository personnelRepository;
	
	@Autowired
	public ComptesServiceImpl(ComptesRepository comptesRepository, PasswordEncoder passwordEncoder, SequenceGeneratorService sequenceGeneratorService,
							  MessageHttpErrorProperties messageHttpErrorProperties, EntrepriseRepository entrepriseRepository,
							  PersonnelRepository personnelRepository, RolesRepository rolesRepository)
	{
		this.comptesRepository = comptesRepository;
		this.passwordEncoder = passwordEncoder;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.entrepriseRepository = entrepriseRepository;
		this.personnelRepository = personnelRepository;
		this.rolesRepository = rolesRepository;
	}

	@Override
	public ComptesDto buildComptesDtoFromComptes(Comptes comptes) {
		if (comptes == null)
		{
			return null;
		}
			
		ComptesDto comptesDto = new ComptesDto();
		comptesDto.setId(comptes.getId());
		comptesDto.setEmail(comptes.getEmail());
		comptesDto.setPassword(comptes.getPassword());
		
		return comptesDto;
		
	}
	
	private Comptes buildComptesFromComptesDto(ComptesDto comptesDto)
	{
		Comptes comptes = new Comptes();
		
		comptes.setId(""+sequenceGeneratorService.generateSequence(Comptes.SEQUENCE_NAME));	
		comptes.setId(comptesDto.getId());		
		comptes.setEmail(comptesDto.getEmail());
		comptes.setPassword(comptesDto.getPassword());

		
		return comptes;
	}


	@Override
	public List<ComptesDto> getAllComptes() {
		
	List<Comptes> listComptes = comptesRepository.findAll();
		
		return listComptes.stream()
				.map(comptes -> buildComptesDtoFromComptes(comptes))
				.filter(comptes -> comptes != null)
				.collect(Collectors.toList());
	}

	@Override
	public ComptesDto getComptesById(String id) {
		
		 Optional<Comptes> comptesOpt = comptesRepository.findById(id);
			if(comptesOpt.isPresent()) {
				return buildComptesDtoFromComptes(comptesOpt.get());
			}
			return null;
	}


	@Override
	public void createNewComptes(@Valid ComptesDto comptesDto) {
		
		comptesRepository.save(buildComptesFromComptesDto(comptesDto));
		
	}


	@Override
	public void updateComptes(@Valid ComptesDto comptesDto) throws ResourceNotFoundException {
		
		Comptes comptes = comptesRepository.findById(comptesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), comptesDto.getId())));
		
		comptes.setEmail(comptesDto.getEmail());
		comptes.setPassword(comptesDto.getPassword());

		 
		comptesRepository.save(comptes);
		
	}


	@Override
	public void deleteComptes(String comptesId) {
		
		comptesRepository.deleteById(comptesId);
		
	}

	@Override
	public void addCompte(String idPersonnel,ComptesDto comptesDto) throws ResourceNotFoundException {
		Personnel personnel = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idPersonnel)));
		comptesDto.setPassword(passwordEncoder.encode(comptesDto.getPassword()));
		final Comptes comptes =  buildComptesFromComptesDto(comptesDto);
		final List<Roles> rolesList = new ArrayList<>();
		comptesDto
				.getRoles()
				.forEach(r -> {
					try {
						rolesList.add(rolesRepository.findByNom(r)
								.orElseThrow(() -> new NotFoundException(r + " not found")));
					} catch (NotFoundException e) {
						throw new RuntimeException(e);
					}
				});
		comptes.setRoles(rolesList);
		comptesRepository.save(comptes);
		personnel.setCompte(comptes);
		personnelRepository.save(personnel);
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Comptes comptes = comptesRepository.findByEmail(email);
		Personnel personnel = null;
		try {
			personnel = personnelRepository.findByCompte(comptes)
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),comptes)));
		} catch (ResourceNotFoundException e) {
			throw new RuntimeException(e);
		}

		if (personnel == null){
			throw new UsernameNotFoundException("User not found in database");
		}else {
			System.out.println("user found in database");
		}
		Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
		personnel.getCompte().getRoles().forEach(roles -> {authorities.add(new SimpleGrantedAuthority(roles.getNom()));
		});
		return new org.springframework.security.core.userdetails.User(personnel.getNom(), personnel.getCompte().getPassword(),authorities);
	}


	@Override
	public List<String> getRoles(String email) {
		Comptes comptes = comptesRepository.findByEmail(email);
		return comptes.getRoles().stream().map(Roles::getNom)
				.collect(Collectors.toList());

	}
}
