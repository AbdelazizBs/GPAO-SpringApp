package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.repository.ArticleRepository;
import com.housservice.housstock.repository.ClientRepository;
import com.housservice.housstock.repository.LigneCommandeClientRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.Deflater;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	private final ArticleRepository articleRepository;
	
	private final SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;

	final
	ClientRepository clientRepository ;
final
LigneCommandeClientRepository ligneCommandeClientRepository;
	final
	PictureRepository pictureRepository;
	@Autowired
	public ArticleServiceImpl (ArticleRepository articleRepository, SequenceGeneratorService sequenceGeneratorService,
							   MessageHttpErrorProperties messageHttpErrorProperties, ClientRepository clientRepository, PictureRepository pictureRepository, LigneCommandeClientRepository ligneCommandeClientRepository)
	{
		this.articleRepository = articleRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.clientRepository = clientRepository;
		this.pictureRepository = pictureRepository;
		this.ligneCommandeClientRepository = ligneCommandeClientRepository;
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
		articleDto.setEtapeProductions(article.getEtapeProductions());
		articleDto.setPicture(article.getPicture());
		articleDto.setNumFicheTechnique(article.getNumFicheTechnique());
		articleDto.setIdClient(article.getClient().getId());
		articleDto.setRaisonSocial(article.getClient().getRaisonSocial());
		articleDto.setDesignation(article.getDesignation());
		articleDto.setMiseEnVeille(article.getMiseEnVeille());
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
		article.setMiseEnVeille(articleDto.getMiseEnVeille());
		article.setEtapeProductions(articleDto.getEtapeProductions());
		article.setTypeProduit(articleDto.getTypeProduit());
		return article;
		
	}
	


	@Override
	public ResponseEntity<Map<String, Object>> getAllArticle(int page, int size) {
		try {
			List<ArticleDto> articles = new ArrayList<ArticleDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Article> pageTuts;
			pageTuts = articleRepository.findArticleByMiseEnVeille(0,paging);
			articles = pageTuts.getContent().stream().map(a -> buildArticleDtoFromArticle(a)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("articles", articles);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<Map<String, Object>> getArticleEnveille(int page, int size) {
		try {
			List<ArticleDto> articles = new ArrayList<ArticleDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Article> pageTuts;
			pageTuts =articleRepository.findArticleByMiseEnVeille(1,paging);
			articles = pageTuts.getContent().stream().map(a -> buildArticleDtoFromArticle(a)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("articles", articles);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





	@Override
	public void setArticleEnVeille(String idArticle) throws ResourceNotFoundException {
		Article article = articleRepository.findById(idArticle)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idArticle)));
		article.setMiseEnVeille(1);
		articleRepository.save(article);
	}
	@Override
	public List<String> getDesignationArticleCient(String idClient) throws ResourceNotFoundException  {
		Client client = clientRepository.findById(idClient).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idClient)));
		List<Article> listArticles = articleRepository.findArticleByClientId(client.getId());
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
	public List<EtapeProduction> getTargetEtapesArticle(String idArticle) throws ResourceNotFoundException  {
		Article article  = articleRepository.findById(idArticle).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
		return article.getEtapeProductions() ;
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
								 MultipartFile[] file) throws ResourceNotFoundException, IOException {
		Picture picture = new Picture();
		ArticleDto articleDto = new ArticleDto();
		if (file.length == 0) {
			ClassLoader classLoader = getClass().getClassLoader();
			File emptyImg = new File(classLoader.getResource("article-vide.jpg").getFile());
			picture.setBytes( Files.readAllBytes(emptyImg.toPath()));
			picture.setFileName("article-vide.jpg");
			picture.setType("image/*");
			pictureRepository.save(picture);
			articleDto.setPicture(picture);

		}
		for (MultipartFile file1 : file) {
			picture.setFileName(file1.getOriginalFilename());
			picture.setType(file1.getContentType());
			try {
				picture.setBytes(file1.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			pictureRepository.save(picture);
		articleDto.setPicture(picture);
		}
		articleDto.setDesignation(designation);
	articleDto.setIdClient(idClient);
	articleDto.setRefClient(refClient);
	articleDto.setReferenceIris(referenceIris);
	articleDto.setTypeProduit(typeProduit);
	articleDto.setRaisonSocial(raisonSocial);
	articleDto.setNumFicheTechnique(numFicheTechnique);
	articleDto.setMiseEnVeille(0);
	articleRepository.save(buildArticleFromArticleDto(articleDto));

	}

	@Override
	public void updateArticle(String referenceIris,
							  String numFicheTechnique,
							  String designation,
							  String typeProduit,
							  String refClient,
							  String raisonSocial,
							  String id,
							  MultipartFile[] file) throws ResourceNotFoundException, IOException {
		Article article = articleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
		if (file != null) {
			for (MultipartFile file1 : file) {
				Picture picture = new Picture();
				picture.setFileName(file1.getOriginalFilename());
				picture.setType(file1.getContentType());
				try {
					picture.setBytes(file1.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				pictureRepository.save(picture);
			article.setPicture(picture);
		}
			}
		Client client = clientRepository.findClientByRaisonSocial(raisonSocial)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), refClient)));
		article.setReferenceIris(referenceIris);
		article.setNumFicheTechnique(numFicheTechnique);
		article.setDesignation(designation);
		article.setClient(client);
		article.setRefClient(refClient);
		article.setTypeProduit(typeProduit);
		articleRepository.save(article);
	}


	@Override
	public void addEtapeToArticle(List<EtapeProduction> etapeProductions , String idArticle ) throws ResourceNotFoundException {
		Article article = articleRepository.findById(idArticle)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idArticle)));
		article.setEtapeProductions(etapeProductions);
//		LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findLigneCommandeClientByArticleId(idArticle)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idArticle)));
//		ligneCommandeClient.setArticle(article);
//		ligneCommandeClientRepository.save(ligneCommandeClient);
		articleRepository.save(article);
	}

//	@Override
//	public void updateContactClient(@Valid Contact contact,String idContact) throws ResourceNotFoundException {
//		Client client =clientRepository.findClientByContactId(idContact)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contact)));
//		Contact contactToUpdate = contactRepository.findById(idContact)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  contact.getId())));
//		contactToUpdate.setNom(contact.getNom());
//		contactToUpdate.setEmail(contact.getEmail());
//		contactToUpdate.setMobile(contact.getMobile());
//		contactToUpdate.setAddress(contact.getAddress());
//		contactToUpdate.setFonction(contact.getFonction());
//		contactToUpdate.setPhone(contact.getPhone());
//		contactRepository.save(contactToUpdate);
//		List<Contact>  contactList= new ArrayList<>();
//		contactList.add(contactToUpdate);
//		contactList.addAll(client.getContact());
//		client.setContact(contactList);
//		clientRepository.save(client);
//
//	}

	@Override
	public void deleteArticle(String articleId) {
		
		articleRepository.deleteById(articleId);

	}

	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size,int enVeille) {

		try {

			List<ArticleDto> articles;
			Pageable paging = PageRequest.of(page, size);
			Page<Article> pageTuts;
			pageTuts = articleRepository.findArticleByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			articles = pageTuts.getContent().stream().map(article -> {
				return buildArticleDtoFromArticle(article);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("articles", articles);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
