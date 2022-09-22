package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.ClientRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private ArticleRepository articleRepository;
	
	private ClientRepository clientRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	
	@Autowired
	public ArticleServiceImpl (ArticleRepository articleRepository,ClientRepository clientRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.articleRepository = articleRepository;
		this.clientRepository = clientRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
	
	
	@Override
	public ArticleDto buildArticleDtoFromArticle(Article article) {
		if (article == null)
		{
			return null;
		}
			
		ArticleDto articleDto = new ArticleDto();
		articleDto.setId(article.getId());
		articleDto.setReferenceIris(article.getReferenceIris());
		articleDto.setReferenceClient(article.getReferenceClient());
		articleDto.setNumFicheTechnique(article.getNumFicheTechnique());
		articleDto.setDesignation(article.getDesignation());
		articleDto.setTypeProduit(article.getTypeProduit());
		articleDto.setIdClient(article.getClient().getId());
		articleDto.setRaisonSocial(article.getClient().getRaisonSocial());
		
		return articleDto;
		
	}


	private Article buildArticleFromArticleDto(ArticleDto articleDto) {
		
		Article article = new Article();
		article.setId(""+sequenceGeneratorService.generateSequence(Article.SEQUENCE_NAME));	
		article.setReferenceIris(articleDto.getReferenceIris());
		article.setReferenceClient(articleDto.getReferenceClient());
		article.setNumFicheTechnique(articleDto.getNumFicheTechnique());
		article.setDesignation(articleDto.getDesignation());
		article.setTypeProduit(articleDto.getTypeProduit());
	    Client client = clientRepository.findById(articleDto.getIdClient()).get();
	    article.setClient(client);
		
		return article;
		
	}
	
	
	@Override
	public List<ArticleDto> getAllArticle() {
		
		List<Article> listArticle = articleRepository.findAll();
		
		return listArticle.stream()
				.map(article -> buildArticleDtoFromArticle(article))
				.filter(article -> article != null)
				.collect(Collectors.toList());
	}

	@Override
	public ArticleDto getArticleById(String id) {
		
	    Optional<Article> articleOpt = articleRepository.findById(id);
		if(articleOpt.isPresent()) {
			return buildArticleDtoFromArticle(articleOpt.get());
		}
		return null;
	}
	
//	@Override
//	 public List<ArticleDto> getArticleByIdClient(final String idClient) {
//		   final Optional<Client> client = clientRepository.findById(idClient);
//		   
//		    return articleRepository.findArticleByClient(client)
//		    		.stream()
//		    		.map(article -> buildArticleDtoFromArticle(article))
//		    		.collect(Collectors.toList());
//		     
//		  }

	@Override
	public void createNewArticle(@Valid ArticleDto articleDto) {
	
		articleRepository.save(buildArticleFromArticleDto(articleDto));
		
	}

	@Override
	public void updateArticle(@Valid ArticleDto articleDto) throws ResourceNotFoundException {
		
		Article article = articleRepository.findById(articleDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), articleDto.getId())));
		
		article.setReferenceIris(articleDto.getReferenceIris());
		article.setNumFicheTechnique(articleDto.getNumFicheTechnique());
		article.setDesignation(articleDto.getDesignation());
		article.setTypeProduit(articleDto.getTypeProduit());
		
		if ( article.getClient() == null || !StringUtils.equals(articleDto.getIdClient(), article.getClient().getId()) )
		{
			Client client = clientRepository.findById(articleDto.getIdClient()).get();
			article.setClient(client);
		}
	
	
		articleRepository.save(article);
	}

	@Override
	public void deleteArticle(String articleId) {
		
		articleRepository.deleteById(articleId);

	}

}
