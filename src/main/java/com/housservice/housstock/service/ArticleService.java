package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.ArticleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService {
	
    public void setArticleEnVeille(String idMachine) throws ResourceNotFoundException;

	public String getIdArticleWithDesignation(String designation) throws ResourceNotFoundException;
	public List<EtapeProduction> getTargetEtapesArticle(String idArticle) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Object>> getArticleEnveille(int page , int size);
    ResponseEntity<Map<String, Object>> getAllArticle(int page , int size);

    public ArticleDto getArticleById(String id);
	
    public ArticleDto buildArticleDtoFromArticle(Article article);

    public void createNewArticle(String referenceIris,
                                 String numFicheTechnique,
                                 String designation,
                                 String typeProduit,
                                 String idClient,
                                 String refClient,
                                 String raisonSocial,
                                 MultipartFile[] file) throws ResourceNotFoundException, IOException;
	
    public void updateArticle(String referenceIris,
                              String numFicheTechnique,
                              String designation,
                              String typeProduit,
                              String refClient,
                              String raisonSocial,
                              String id,
                              MultipartFile[] file) throws ResourceNotFoundException, IOException;
    public void addEtapeToArticle(List<EtapeProduction> etapeProductions, String idArticle ) throws ResourceNotFoundException;

    public void deleteArticle(String articleId);
    public ResponseEntity<Map<String, Object>> search(String textToFind,int page, int size,int  enVeille);


}

