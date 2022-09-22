package com.housservice.housstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Article;

import com.housservice.housstock.model.Client;

public interface ArticleRepository extends MongoRepository<Article, String>{

	//Optional<Article> findByIdClient(String idClient);
	
	//List<Article> findArticleByClient(Optional<Client> client);
		
	//Optional  <Article> findArticleById(String id);

	// Optional <Article> findArticleByReferenceIris(String referenceIris);

	// List<Article> findArticleByClient(Client client);

}
