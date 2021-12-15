package com.housservice.housstock.service;

import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.dto.ArticleDto;

public interface ArticleService {
	
	public List<ArticleDto> getAllArticle();
	
    public ArticleDto getArticleById(String id);
	
    public ArticleDto buildArticleDtoFromArticle(Article article);

    public void createNewArticle(@Valid ArticleDto articleDto);
	
    public void updateArticle(@Valid ArticleDto articleDto) throws ResourceNotFoundException;
    
    public void deleteArticle(String articleId);

}
