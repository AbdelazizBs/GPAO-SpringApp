package com.housservice.housstock.controller.article;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.model.EtapeProduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.service.ArticleService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/article")
@Api(tags = {"Articles Management"})
public class ArticleController {
	
	  	private ArticleService articleService;

	    private final MessageHttpErrorProperties messageHttpErrorProperties;
	    
	    
	    @Autowired
		  public ArticleController(ArticleService articleService, MessageHttpErrorProperties messageHttpErrorProperties) {
			this.articleService = articleService;
			this.messageHttpErrorProperties = messageHttpErrorProperties;
		  }
	
	    @GetMapping("/getAllArticles")
		 public List< ArticleDto > getAllArticle() {
			 		
			 return articleService.getAllArticle();
			 	 
		 }
	
	    
	    @GetMapping("/article/{id}")
		@ApiOperation(value = "service to get one Article by Id.")
		  public ResponseEntity < ArticleDto > getArticleById(
				  @ApiParam(name = "id", value="id of article", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String articleId)
		  throws ResourceNotFoundException {
	    	ArticleDto article = articleService.getArticleById(articleId);
			  if (article == null) {
				  ResponseEntity.badRequest();
			  }
		      return ResponseEntity.ok().body(article);
		  }
	    
	    @PutMapping("/addArticle")
		  public ResponseEntity<String> createArticle(final  String referenceIris,
													  final  String numFicheTechnique,
													  final  String designation,
													  final  String typeProduit,
													  final  String idClient,
													  final  String refClient,
													  final  String raisonSocial,
													  final  String prix
				, MultipartFile picture) throws ResourceNotFoundException, IOException {
			  
	    	  articleService.createNewArticle(referenceIris,numFicheTechnique,designation,typeProduit,idClient,refClient,raisonSocial,prix,picture);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		  }

	    @PutMapping("/updateArticle/{articleId}")
		  public ResponseEntity <String> updateArticle(
				  @ApiParam(name = "id", value="id of article", required = true)
				  @PathVariable(value = "articleId", required = true) @NotEmpty(message = "{http.error.0001}")
				  String articleId,
				  final  String referenceIris,
				  final  String numFicheTechnique,
				  final  String designation,
				  final  String typeProduit,
				  final  String idClient,
				  final  String refClient,
				  final  String raisonSocial,
				  final  String prix,
				  MultipartFile file) throws ResourceNotFoundException, IOException {
			  
	    	  articleService.updateArticle(referenceIris,numFicheTechnique,designation,typeProduit,idClient,refClient,raisonSocial,prix,articleId,file);
		      
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
		  }

	    
		  @DeleteMapping("/article/{id}")
		  @ApiOperation(value = "service to delete one Article by Id.")
		  public Map < String, Boolean > deleteArticle(
				  @ApiParam(name = "id", value="id of article", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String articleId) {

			  articleService.deleteArticle(articleId);
		      Map < String, Boolean > response = new HashMap < > ();
		      response.put("deleted", Boolean.TRUE);
		      return response;
		  }


	@GetMapping("/getDesignationArticleCient/{idClient}")
	@ApiOperation(value = "service to get List of  designation ArticleClient  by idClient.")
	public List < String > getDesignationArticleCient(
			@ApiParam(name = "idClient", value="id of client", required = true)
			@PathVariable(value = "idClient", required = true) @NotEmpty(message = "{http.error.0001}") String idClient)
			throws ResourceNotFoundException {
		return articleService.getDesignationArticleCient(idClient);
	}

		@GetMapping("/getRefIrisAndClient/{designation}")
	@ApiOperation(value = "service to get Ref Iris And Client  .")
	public List<String>  getRefIrisAndClientAndIdArticle(
			@ApiParam(name = "designation", value="designation of article", required = true)
			@PathVariable(value = "designation", required = true) @NotEmpty(message = "{http.error.0001}") String designation)
			throws ResourceNotFoundException {
		return articleService.getRefIrisAndClientAndIdArticle(designation);
	}

	@GetMapping("/getIdArticleWithDesignation/{designation}")
	@ApiOperation(value = "service to get id Article .")
	public String  getIdArticleWithDesignation(
			@ApiParam(name = "designation", value="designation of article", required = true)
			@PathVariable(value = "designation", required = true) @NotEmpty(message = "{http.error.0001}") String designation)
			throws ResourceNotFoundException {
		return articleService.getIdArticleWithDesignation(designation);
	}

	@PutMapping("/addEtapeToArticle/{idArticle}")
	public ResponseEntity <String> addEtapeToArticle(
			@ApiParam(name = "idArticle", value="id of client", required = true)
			@PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}")  String idArticle,
			@Valid @RequestBody(required = true) List<EtapeProduction> productions) throws ResourceNotFoundException {
		articleService.addEtapeToArticle(productions,idArticle);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}


	@GetMapping("/getTargetEtapesArticle/{idArticle}")
	@ApiOperation(value = "service to get id Article .")
	public List<EtapeProduction>  getTargetEtapesArticle(
			@ApiParam(name = "idArticle", value="idARTICLE of article", required = true)
			@PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}") String idArticle)
			throws ResourceNotFoundException {
		return articleService.getTargetEtapesArticle(idArticle);
	}


	@GetMapping("/getArticleEnveille")
	public List<Article> getArticleEnveille() {
		return articleService.getArticleEnveille();
	}
	  
}
	  
	  
	  
	  
	  

