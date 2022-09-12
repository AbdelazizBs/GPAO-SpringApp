package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private ArticleRepository articleRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;

	final
	ClientRepository clientRepository ;

	
	@Autowired
	public ArticleServiceImpl (ArticleRepository articleRepository, SequenceGeneratorService sequenceGeneratorService,
							   MessageHttpErrorProperties messageHttpErrorProperties, ClientRepository clientRepository)
	{
		this.articleRepository = articleRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.clientRepository = clientRepository;
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
		articleDto.setRefClient(article.getRefClient());
		articleDto.setNumFicheTechnique(article.getNumFicheTechnique());
		articleDto.setIdClient(article.getClient().getId());
		articleDto.setRaisonSocial(article.getClient().getRaisonSocial());
		articleDto.setDesignation(article.getDesignation());
		articleDto.setTypeProduit(article.getTypeProduit());
		
		return articleDto;
		
	}


	private Article buildArticleFromArticleDto(ArticleDto articleDto) throws ResourceNotFoundException {
		
		Article article = new Article();
		article.setId(""+sequenceGeneratorService.generateSequence(Article.SEQUENCE_NAME));	
		article.setReferenceIris(articleDto.getReferenceIris());
		article.setRefClient(articleDto.getRefClient());
		article.setNumFicheTechnique(articleDto.getNumFicheTechnique());
		Client client = clientRepository.findById(articleDto.getIdClient()).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),articleDto.getIdClient())));
		article.setClient(client);
		article.setDesignation(articleDto.getDesignation());
		article.setTypeProduit(articleDto.getTypeProduit());
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
	public List<String> getDesignationArticleCient(String idClient) throws ResourceNotFoundException  {
		Client client = clientRepository.findById(idClient).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
		List<Article> listArticles = articleRepository.findArticleByClient(client);
		return listArticles.stream()
				.map(Article::getDesignation)
				.collect(Collectors.toList());
	}
	@Override
	public List<String> getRefIrisAndClientAndIdArticle(String designation) throws ResourceNotFoundException  {
		Article article  = articleRepository.findArticleByDesignation(designation).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), designation)));
		ArrayList<String> refIrisAndClient = new ArrayList<>();
		refIrisAndClient.add(article.getId());
		refIrisAndClient.add(article.getReferenceIris());
		refIrisAndClient.add(article.getRefClient());
		return refIrisAndClient ;
	}
	@Override
	public ArticleDto getArticleById(String id) {
		
	    Optional<Article> articleOpt = articleRepository.findById(id);
		if(articleOpt.isPresent()) {
			return buildArticleDtoFromArticle(articleOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewArticle(@Valid ArticleDto articleDto) throws ResourceNotFoundException {
	
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
	
		articleRepository.save(article);
	}

	@Override
	public void deleteArticle(String articleId) {
		
		articleRepository.deleteById(articleId);

	}

}
