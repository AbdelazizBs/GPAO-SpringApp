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
import com.housservice.housstock.model.Categorie;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CategorieRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private ArticleRepository articleRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	private CategorieRepository categorieRepository;
	
	@Autowired
	public ArticleServiceImpl (ArticleRepository articleRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties,CategorieRepository categorieRepository)
	{
		this.articleRepository = articleRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.categorieRepository = categorieRepository;
	}
	
	
	@Override
	public ArticleDto buildArticleDtoFromArticle(Article article) {
		if (article == null)
		{
			return null;
		}
			
		ArticleDto articleDto = new ArticleDto();
		articleDto.setId(article.getId());
		articleDto.setCodeArticle(article.getCodeArticle());
		articleDto.setDesignation(article.getDesignation());
		articleDto.setPrixUnitaireHt(article.getPrixUnitaireHt());
		articleDto.setTauxTva(article.getTauxTva());
		articleDto.setPrixUnitaireTtc(article.getPrixUnitaireTtc());
		articleDto.setIdCategorie(article.getCategorie().getId());
		articleDto.setDesignationCategorie(article.getCategorie().getDesignation());
		
		return articleDto;
		
	}


	private Article buildArticleFromArticleDto(ArticleDto articleDto) {
		
		Article article = new Article();
		article.setId(""+sequenceGeneratorService.generateSequence(Article.SEQUENCE_NAME));	
		article.setCodeArticle(articleDto.getCodeArticle());
		article.setDesignation(articleDto.getDesignation());
		article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
		article.setTauxTva(articleDto.getTauxTva());
		article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
		Categorie cat = categorieRepository.findById(articleDto.getIdCategorie()).get();
		article.setCategorie(cat);
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

	
	@Override
	public void createNewArticle(@Valid ArticleDto articleDto) {
	
		articleRepository.save(buildArticleFromArticleDto(articleDto));
		
	}

	@Override
	public void updateArticle(@Valid ArticleDto articleDto) throws ResourceNotFoundException {
		
		Article article = articleRepository.findById(articleDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), articleDto.getId())));
		
		article.setCodeArticle(articleDto.getCodeArticle());
		article.setDesignation(articleDto.getDesignation());
		article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
		article.setTauxTva(articleDto.getTauxTva());
		article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
		
		if(article.getCategorie() == null || !StringUtils.equals(articleDto.getIdCategorie(), article.getCategorie().getId())) 
		{
			Categorie categorie = categorieRepository.findById(articleDto.getIdCategorie()).get();
			article.setCategorie(categorie);
		}
		
		articleRepository.save(article);
	}

	@Override
	public void deleteArticle(String articleId) {
		
		articleRepository.deleteById(articleId);

		
	}

}
