package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.model.dto.CommandeClientDto;
import com.housservice.housstock.model.dto.CommandeClientSuiviDto;
import com.housservice.housstock.model.dto.CommandeSuiviDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;



public interface CommandeClientService {

    Optional<CommandeClient> getCommandeClientById(String id);

    void createNewCommandeClient(CommandeClientDto commandeClientDto) throws ResourceNotFoundException;
    void UpdateCommandeClient(String idCommandeClient,CommandeClient commandeClient) throws ResourceNotFoundException;
    void deleteCommandeClient(CommandeClient commandeClient);
    void deleteCommandeClientSelected(List<String> idCommandeClientsSelected);
    public ResponseEntity<Map<String, Object>> getIdCommandeClients(String numBcd) throws ResourceNotFoundException;
    int getallCommandeClient();
    public ResponseEntity<Map<String, Object>> onSortActiveCommandeClient(int page, int size, String field, String order);
    List<String> getAllClients();
    ResponseEntity<Map<String, Object>> getAllCommandeClient(int page , int size);
    void addArticleCommandeClient(ArticleDto articleDto, String idCommandeClient ) throws ResourceNotFoundException;
    void updateArticleCommandeClient( ArticleDto article, String idArticle) throws ResourceNotFoundException;
    void deleteArticleCommandeClient(String idArticle) throws ResourceNotFoundException;
    ResponseEntity<Map<String, Object>> getCommandeClientNotActive(int page, int size);

    void miseEnVeille(String id, CommandeClientSuiviDto commandeClientSuiviDto) throws ResourceNotFoundException;



}
