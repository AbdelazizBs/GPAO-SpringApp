package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.CompteDto;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CompteService {
    public String login(LoginRequest loginRequest) throws LoginException;
    public void add(CompteDto compteDto)throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getAllCompte(int page , int size);
    void updateCompte(CompteDto compteDto, String id) throws ResourceNotFoundException;
    public ResponseEntity<Map<String, Object>> onSortActiveCompte(int page, int size, String field, String order);
    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size);
    public List<String> getAllRole();
    public void deleteCompte(Compte compte);
    Optional<Compte> getCompteById(String username);
    Optional<Personnel> getPersonnelById(String username);
    List<String> getAllPer();
    public void miseEnVeille(String idCompte) throws ResourceNotFoundException;
    public ResponseEntity<Map<String, Object>> getAllCompteVeille(int page, int size);
    public void Restaurer(String id) throws ResourceNotFoundException;
}