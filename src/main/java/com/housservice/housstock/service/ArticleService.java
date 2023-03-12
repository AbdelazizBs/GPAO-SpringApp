package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;

public interface ArticleService {
    void deleteArticleCommande(String idArticle) throws ResourceNotFoundException;
}
