package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.housservice.housstock.mapper.CommandMapper;
import com.housservice.housstock.mapper.CompteMapper;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.repository.RolesRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public List<ComptesDto> getAllComptes() {
		
	List<Comptes> listComptes = comptesRepository.findAll();
		
		return listComptes.stream()
				.map(comptes -> CompteMapper.MAPPER.toComptesDto(comptes))
				.filter(comptes -> comptes != null)
				.collect(Collectors.toList());
	}

	@Override
	public ComptesDto getComptesById(String id) {
		
		 Optional<Comptes> comptesOpt = comptesRepository.findById(id);
			if(comptesOpt.isPresent()) {
				return CompteMapper.MAPPER.toComptesDto(comptesOpt.get());
			}
			return null;
	}



	@Override
	public void updateCompte(String  idPersonnel ,@Valid ComptesDto comptesDto) throws ResourceNotFoundException {
		Comptes compte = comptesRepository.findById(comptesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), comptesDto.getId())));
		Personnel personnel = personnelRepository.findPersonnelByCompteId(compte.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idPersonnel)));
		Personnel personnel1 = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idPersonnel)));
		personnel.setCompte(null);
		personnelRepository.save(personnel);
		compte.setIdPersonnel(idPersonnel);
		compte.setEmail(comptesDto.getEmail());
		compte.setPersonnelName(personnel1.getNom());
		compte.setPassword(passwordEncoder.encode(comptesDto.getPassword()));
		compte.setRole(rolesRepository.findByNom(comptesDto.getRoleName())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),comptesDto.getRoleName()))));
		comptesRepository.save(compte);
		personnel1.setCompte(compte);
		personnelRepository.save(personnel1);
	}


	@Override
	public void deleteCompte(String idCompte) throws ResourceNotFoundException {
		Comptes compte = comptesRepository.findById(idCompte)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idCompte)));
		Personnel personnel = personnelRepository.findByCompte(compte)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idCompte)));
		personnel.setCompte(null);
		personnelRepository.save(personnel);
		comptesRepository.deleteById(idCompte);
	}

	@Override
	public void addCompte(String idPersonnel,ComptesDto comptesDto) throws ResourceNotFoundException {
		Personnel personnel = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idPersonnel)));
		if (comptesRepository.existsByEmail(comptesDto.getEmail())){
			throw new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0006(),comptesDto.getEmail()));
		}
		Comptes comptes = CompteMapper.MAPPER.toComptes(comptesDto);
		comptes.setPassword(passwordEncoder.encode(comptesDto.getPassword()));
		comptes.setIdPersonnel(idPersonnel);
		comptes.setEnVeille(false);
		comptes.setDatelastlogin(null);
		comptes.setRole(rolesRepository.findByNom(comptesDto.getRoleName())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),comptesDto.getRoleName()))));
		comptesRepository.save(comptes);
		personnel.setCompte(comptes);
		personnelRepository.save(personnel);
	}

	@Override
	public void restaurer(String idCompte) throws ResourceNotFoundException {
		Comptes comptes = comptesRepository.findById(idCompte)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idCompte)));
		comptes.setEnVeille(false);
		comptesRepository.save(comptes);
	}
	@Override
	public void miseEnVeille(String idCompte) throws ResourceNotFoundException {
		Comptes comptes = comptesRepository.findById(idCompte)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),idCompte)));
		comptes.setEnVeille(true);
		comptesRepository.save(comptes);
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Comptes comptes = comptesRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),email)));
		Personnel personnel = null;
		try {
			personnel = personnelRepository.findPersonnelByCompteId(comptes.getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),comptes)));
		} catch (ResourceNotFoundException e) {
			throw new RuntimeException(e);
		}

		if (personnel == null){
			throw new UsernameNotFoundException("User not found in database");
		}else if (comptes.isEnVeille()){
			throw new UsernameNotFoundException("User is in Veille");
		}else {
			System.out.println("user found in database");
			comptes.setDatelastlogin(new Date());
			comptesRepository.save(comptes);
		}
		Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(personnel.getCompte().getRole().getNom()));
		return new org.springframework.security.core.userdetails.User(personnel.getCompte().getEmail(), personnel.getCompte().getPassword(),authorities);
	}


	@Override
	public String getRoles(String email) {
		Comptes comptes = comptesRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),email)));
		return comptes.getRole().getNom();

	}
	@Override
	public ResponseEntity<Map<String, Object>> getAllCompte(int page, int size) {
		try {
			List<ComptesDto> comptes;
			Pageable paging = PageRequest.of(page, size);
			Page<Comptes> pageTuts;
			pageTuts = comptesRepository.findComptesByEnVeille(false, paging);
			comptes = pageTuts.getContent().stream().map(comptes1 -> {
				return CompteMapper.MAPPER.toComptesDto(comptes1);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("comptes", comptes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Override
	public ResponseEntity<Map<String, Object>> getAllCompteEnVeille(int page, int size) {
		try {
			List<ComptesDto> comptes;
			Pageable paging = PageRequest.of(page, size);
			Page<Comptes> pageTuts;
			pageTuts = comptesRepository.findComptesByEnVeille(true, paging);
			comptes = pageTuts.getContent().stream().map(comptes1 -> {
				return CompteMapper.MAPPER.toComptesDto(comptes1);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("comptes", comptes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
