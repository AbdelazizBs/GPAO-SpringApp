package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Categorie;
import com.housservice.housstock.model.dto.CategorieDto;
import com.housservice.housstock.repository.CategorieRepository;

@Service
public  class CategorieServiceImpl implements CategorieService {

	private CategorieRepository categorieRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	@Autowired
	public CategorieServiceImpl (CategorieRepository categorieRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.categorieRepository = categorieRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		
	}
	
	@Override
	public CategorieDto buildCategorieDtoFromCategorie(Categorie categorie) {
		
		if (categorie == null)
		{
			return null;
		}
			
		CategorieDto categorieDto = new CategorieDto();
		categorieDto.setId(categorie.getId());
		categorieDto.setCode(categorie.getCode());
		categorieDto.setDesignation(categorie.getDesignation());
		//TODO
		/*
		 * if (categorie.getListIdArticles() != null) { List<ArticleDto>
		 * listArticleDtoDeCategorie = categorie.getListIdArticles().stream()
		 * .map(articleId -> buildArticleDtoFromArticle(getCategorieById(articleId)))
		 * .filter(art -> art != null) .collect(Collectors.toList());
		 * 
		 * categorieDto.setListArticles(listArticleDtoDeCategorie); }
		 */
		

		return categorieDto;
	}
	
	/*
	 * private CategorieDto buildCategorieDtoFromCategorie(Optional<Categorie>
	 * categorieOp) { if (categorieOp.isPresent()) { return
	 * buildCategorieDtoFromCategorie(categorieOp.get()); } return null; }
	 */
	
	
	
	public Categorie buildCategorieFromCategorieDto(CategorieDto categorieDto) {
		
		Categorie categorie = new Categorie();
		categorie.setId(""+sequenceGeneratorService.generateSequence(Categorie.SEQUENCE_NAME));
		categorie.setCode(categorieDto.getCode());
		categorie.setDesignation(categorieDto.getDesignation());
		return categorie;
	}
	
	@Override
	public List<CategorieDto> getAllCategorie() {
		
	List<Categorie> listCategorie = categorieRepository.findAll();
		
		return listCategorie.stream()
				.map(categorie -> buildCategorieDtoFromCategorie(categorie))
				.filter(categorie -> categorie != null)
				.collect(Collectors.toList());
	}

	@Override
	public CategorieDto getCategorieById(String id) {
		  Optional<Categorie> categorieOpt = categorieRepository.findById(id);
			if(categorieOpt.isPresent()) {
				return buildCategorieDtoFromCategorie(categorieOpt.get());
			}
			return null;
	}

	
	@Override
	public void createNewCategorie(@Valid CategorieDto categorieDto) {
		
		categorieRepository.save(buildCategorieFromCategorieDto(categorieDto));
		
	}

	@Override
	public void updateCategorie(@Valid CategorieDto categorieDto) throws ResourceNotFoundException {
		
		Categorie categorie = categorieRepository.findById(categorieDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), categorieDto.getId())));
		
		categorie.setCode(categorieDto.getCode());
		categorie.setDesignation(categorieDto.getDesignation());
	//TODO	
	// liste article
		categorieRepository.save(categorie);
		
	}

	@Override
	public void deleteCategorie(String categorieId) {
		
		categorieRepository.deleteById(categorieId);
		
	}

	

}
