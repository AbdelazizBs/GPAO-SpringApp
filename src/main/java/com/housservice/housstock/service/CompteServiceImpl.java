package com.housservice.housstock.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.CommandeMapper;
import com.housservice.housstock.mapper.CompteMapper;
import com.housservice.housstock.mapper.FournisseurMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.CommandeDto;
import com.housservice.housstock.model.dto.CompteDto;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.CompteRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.RolesRepository;
import org.apache.tomcat.jni.PasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.security.auth.login.LoginException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class CompteServiceImpl implements CompteService{
    private final CompteRepository compteRepository;
    private final RolesRepository rolesRepository;
    private final PersonnelRepository personnelRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public CompteServiceImpl(CompteRepository compteRepository,RolesRepository rolesRepository,MessageHttpErrorProperties messageHttpErrorProperties,PersonnelRepository personnelRepository) {
        this.rolesRepository = rolesRepository;
        this.compteRepository = compteRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.personnelRepository=personnelRepository;
    }

    @Override
    public String login(LoginRequest loginRequest) throws LoginException {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        Compte compte = compteRepository.findByEmail(loginRequest.getEmail());
        if (compte == null) {
            throw new LoginException("Invalid username or password.");
        }
        if (bcrypt.matches(loginRequest.getPassword(),compte.getPassword())) {
            String token = generateToken(compte);
            compte.setDatelastlogin(new Date());
            compteRepository.save(compte);
            return token;
        } else {
            throw new LoginException("Invalid username or password.");
        }
    }

    private String generateToken(Compte compte) {
        Algorithm algorithm = Algorithm.HMAC256("test".getBytes());
        String token = JWT.create()
                .withSubject(compte.getEmail())
                .withClaim("roles",compte.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() +24 * 60 * 60 * 1000))
                .sign(algorithm);
        return token;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(CompteDto compteDto) throws ResourceNotFoundException {
        try {
            if (compteRepository.existsByEmail(compteDto.getEmail())) {
                throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà.");
            }
            compteDto.setMiseEnVeille(false);
            compteDto.setPassword(passwordEncoder.encode(compteDto.getPassword()));
            Compte compte = CompteMapper.MAPPER.toCompte(compteDto);
            compteRepository.save(compte);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    @Override
    public void Restaurer(String id) throws ResourceNotFoundException {
        System.out.println(id);
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        compte.setMiseEnVeille(false);
        compteRepository.save(compte);
    }
    @Override
    public void updateCompte(CompteDto compteDto,String id) throws ResourceNotFoundException {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  id)));
        if(compteDto.getPassword()!=""){
            compteDto.setPassword(passwordEncoder.encode(compteDto.getPassword()));
            compte.setPassword(compteDto.getPassword());
        }
        if (compteDto.getEmail()==""){
            throw new IllegalArgumentException("Veuillez renseigner les champs obligatoires.");

        }
        if (compteRepository.existsByEmail(compteDto.getEmail())) {
            if(!compte.getEmail().equals(compteDto.getEmail())){
                throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà.");
            }
        }
            compte.setEmail(compteDto.getEmail());
            compte.setRole(compteDto.getRole());
        compteRepository.save(compte);
    }
    @Override
    public Optional<Compte> getCompteById(String username){
        return compteRepository.findCompteByEmail(username);
    }
    @Override
    public Optional<Personnel> getPersonnelById(String username){
        Compte compte= compteRepository.findCompteByEmail(username).get();
        return personnelRepository.findByFullName(compte.getIdPersonnel());
    }
    @Override
    public void deleteCompte(Compte compte) {
        compteRepository.delete(compte);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCompte(int page, int size) {
        try {
            List<CompteDto> comptes = new ArrayList<CompteDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Compte> pageTuts;
            pageTuts =  compteRepository.findMachineByMiseEnVeille(false, paging);
            comptes = pageTuts.getContent().stream().map(compte -> {
                return CompteMapper.MAPPER.toCompteDto(compte);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("comptes", comptes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllCompteVeille(int page, int size) {
        try {
            List<CompteDto> comptes = new ArrayList<CompteDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Compte> pageTuts;
            pageTuts =  compteRepository.findMachineByMiseEnVeille(true, paging);
            comptes = pageTuts.getContent().stream().map(compte -> {
                return CompteMapper.MAPPER.toCompteDto(compte);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("comptes", comptes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveCompte(int page, int size, String field, String order) {
        try {
            List<CompteDto> compteDtos ;
            Page<Compte> pageTuts;
            if (order.equals("1")){
                pageTuts = compteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = compteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            compteDtos = pageTuts.getContent().stream().map(compte -> {
                return CompteMapper.MAPPER.toCompteDto(compte);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("comptes", compteDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size) {
        try {
            List<CompteDto> Comptes;
            Pageable paging = PageRequest.of(page, size);
            Page<Compte> pageTuts;
            pageTuts = compteRepository.findCompteByTextToFind(textToFind, paging);
            Comptes = pageTuts.getContent().stream().map(Compte -> {
                return CompteMapper.MAPPER.toCompteDto(Compte);
            }).collect(Collectors.toList());
            Comptes= Comptes.stream().collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("Comptes", Comptes);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public List<String> getAllRole() {
        List<Roles> roles = rolesRepository.findAll();
        return roles.stream()
                .map(Roles::getRole)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllPer() {
        List<Personnel> personnels = personnelRepository.findAll();
        return personnels.stream()
                .map(Personnel::getFullName)
                .collect(Collectors.toList());
    }

    @Override
    public void miseEnVeille(String idCompte) throws ResourceNotFoundException {
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idCompte)));
        compte.setMiseEnVeille(true);
        compteRepository.save(compte);

    }
}
