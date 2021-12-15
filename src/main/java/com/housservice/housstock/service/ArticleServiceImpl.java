package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Categorie;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.CategorieRepository;

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
	public ArticleDto buildArticleDtoFromArticle(Article article) {
		if (article == null)
		{
			return null;
		}
		
		return ArticleDto.builder()
				.id(article.getId())
				.codeArticle(article.getCodeArticle())
				.designation(article.getDesignation())
				.prixUnitaireHt(article.getPrixUnitaireHt())
				.tauxTva(article.getTauxTva())
				.prixUnitaireTtc(article.getPrixUnitaireTtc())
				.photo(article.getPhoto())
				.idCategorie(article.getCategorie().getId())
				.designationCategorie(article.getCategorie().getDesignation())	
				.build();
	}

	
	private Article buildArticleFomArticleDto(ArticleDto articleDto) {
		
		return Article.builder()
				.id(""+sequenceGeneratorService.generateSequence(Article.SEQUENCE_NAME))
				.codeArticle(articleDto.getCodeArticle())
				.designation(articleDto.getDesignation())
				.prixUnitaireHt(articleDto.getPrixUnitaireHt())
				.tauxTva(articleDto.getTauxTva())
				.prixUnitaireTtc(articleDto.getPrixUnitaireTtc())
				.photo(articleDto.getPhoto())
				.categorie(categorieRepository.findById(articleDto.getIdCategorie()).get())
				.build();
		
	}
	
	
	@Override
	public void createNewArticle(@Valid ArticleDto articleDto) {
	
		articleRepository.save(buildArticleFomArticleDto(articleDto));
		
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
		article.setPhoto(articleDto.getPhoto());
		
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
