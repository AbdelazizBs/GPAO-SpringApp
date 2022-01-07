package com.housservice.housstock.controller.article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Articles Management"})
public class ArticleController {
	
	  	private ArticleService articleService;
	  
	    private final MessageHttpErrorProperties messageHttpErrorProperties;
	    
	    
	    @Autowired
		  public ArticleController(ArticleService articleService, MessageHttpErrorProperties messageHttpErrorProperties) {
			this.articleService = articleService;
			this.messageHttpErrorProperties = messageHttpErrorProperties;
		  }
	
	    @GetMapping("/article")
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
	    
	    @PutMapping("/article")
		  public ResponseEntity<String> createArticle(@Valid @RequestBody ArticleDto articleDto) {
			  
	    	  articleService.createNewArticle(articleDto);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		  }
	    
	    @PutMapping("/article/{id}")
		  public ResponseEntity <String> updateArticle(
				  @ApiParam(name = "id", value="id of article", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String articleId,
		          @Valid @RequestBody(required = true) ArticleDto articleDto) throws ResourceNotFoundException {
			  
	    	  articleService.updateArticle(articleDto);
		      
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
	  
}
	  
	  
	  
	  
	  

