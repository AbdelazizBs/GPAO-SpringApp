package com.housservice.housstock.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private ArticleRepository articleRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;

	final
	ClientRepository clientRepository ;

	final
	PictureRepository pictureRepository;
	@Autowired
	public ArticleServiceImpl (ArticleRepository articleRepository, SequenceGeneratorService sequenceGeneratorService,
							   MessageHttpErrorProperties messageHttpErrorProperties, ClientRepository clientRepository, PictureRepository pictureRepository)
	{
		this.articleRepository = articleRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.clientRepository = clientRepository;
		this.pictureRepository = pictureRepository;
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
		articleDto.setPrix(article.getPrix());
		articleDto.setPicture(article.getPicture());
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
		article.setPrix(articleDto.getPrix());
		article.setNumFicheTechnique(articleDto.getNumFicheTechnique());
		Client client = clientRepository.findById(articleDto.getIdClient()).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),articleDto.getIdClient())));
		article.setClient(client);
		Picture picture = pictureRepository.findById(articleDto.getPicture().getId()).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),articleDto.getPicture().getId())));
		article.setPicture(picture);
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
	public String getIdArticleWithDesignation(String designation) throws ResourceNotFoundException  {
		Article article  = articleRepository.findArticleByDesignation(designation).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), designation)));
		return article.getId() ;
	}
	@Override
	public ArticleDto getArticleById(String id) {
		
	    Optional<Article> articleOpt = articleRepository.findById(id);
		if(articleOpt.isPresent()) {
			return buildArticleDtoFromArticle(articleOpt.get());
		}
		return null;
	}

	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}
	
	@Override
	public void createNewArticle(String referenceIris,
								 String numFicheTechnique,
								 String designation,
								 String typeProduit,
								 String idClient,
								 String refClient,
								 String raisonSocial,
								 String prix,
								 MultipartFile file) throws ResourceNotFoundException, IOException {
		ArticleDto articleDto = new ArticleDto();
		final Picture fileDBB = new Picture(file.getOriginalFilename(), file.getBytes(),file.getContentType());
		pictureRepository.save(fileDBB);
		articleDto.setPicture(fileDBB);
		articleDto.setDesignation(designation);
	articleDto.setPrix(prix);
	articleDto.setIdClient(idClient);
	articleDto.setRefClient(refClient);
	articleDto.setReferenceIris(referenceIris);
	articleDto.setTypeProduit(typeProduit);
	articleDto.setRaisonSocial(raisonSocial);
	articleDto.setNumFicheTechnique(numFicheTechnique);

		articleRepository.save(buildArticleFromArticleDto(articleDto));
		
	}

	@Override
	public void updateArticle(String referenceIris,
							  String numFicheTechnique,
							  String designation,
							  String typeProduit,
							  String idClient,
							  String refClient,
							  String raisonSocial,
							  String prix,
							  String id,
							  MultipartFile file) throws ResourceNotFoundException, IOException {
		
		Article article = articleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
		Picture picture = pictureRepository.findPictureByBytes(compressBytes(file.getBytes()));
		if (picture!=null){
			picture.setBytes(compressBytes(file.getBytes()));
			picture.setFileName(file.getOriginalFilename());
			pictureRepository.save(picture);
			article.setPicture(picture);
		}else {
			final Picture fileDBB = new Picture(file.getOriginalFilename(), compressBytes(file.getBytes()),file.getContentType());
			pictureRepository.save(fileDBB);
			article.setPicture(fileDBB);
		}
		article.setReferenceIris(referenceIris);
		article.setNumFicheTechnique(numFicheTechnique);
		article.setDesignation(designation);
		article.setPrix(prix);
		article.setPrix(idClient);
		article.setPrix(refClient);
		article.setPrix(raisonSocial);
		article.setTypeProduit(typeProduit);
	
		articleRepository.save(article);
	}

	@Override
	public void deleteArticle(String articleId) {
		
		articleRepository.deleteById(articleId);

	}

}
