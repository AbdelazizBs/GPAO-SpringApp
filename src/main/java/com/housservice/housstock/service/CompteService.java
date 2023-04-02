package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Commande;
import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.LoginRequest;
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
    Optional<Compte> getCompteById(String id);

}
