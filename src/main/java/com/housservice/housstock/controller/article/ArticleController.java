package com.housservice.housstock.controller.article;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.ArticleDto;
import com.housservice.housstock.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/article")
@Api(tags = {"Articles Management"})
@Validated
public class ArticleController {
	
	  	private final ArticleService articleService;

	    private final MessageHttpErrorProperties messageHttpErrorProperties;
	    
	    
	    @Autowired
		  public ArticleController(ArticleService articleService, MessageHttpErrorProperties messageHttpErrorProperties) {
			this.articleService = articleService;
			this.messageHttpErrorProperties = messageHttpErrorProperties;
		  }

	@GetMapping("/getAllArticles")
	public ResponseEntity<Map<String, Object>> getAllArticle(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return articleService.getAllArticle(page,size);

	}
	@GetMapping("/getArticleEnveille")
	public ResponseEntity<Map<String, Object>> getArticleEnveille(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return articleService.getArticleEnveille(page,size);

	}





	@PutMapping("/setArticleEnVeille/{idArticle}")
	public ResponseEntity <String> setArticleEnVeille(
			@ApiParam(name = "idArticle", value="id of machine", required = true)
			@PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}")  String idArticle) throws ResourceNotFoundException {

		articleService.setArticleEnVeille(idArticle);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}
	    @GetMapping("/getArticleById/{id}")
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
		  public ResponseEntity<String> createArticle(@RequestParam("referenceIris") @NotEmpty String referenceIris,
													  @RequestParam("numFicheTechnique") @NotEmpty String numFicheTechnique,
													  @RequestParam("designation") @NotEmpty String designation,
													  @RequestParam("typeProduit") @NotEmpty String typeProduit,
													  @RequestParam("idClient")   String idClient,
													  @RequestParam("refClient") @NotEmpty  String refClient,
													  @RequestParam("raisonSocial")@NotEmpty   String raisonSocial,
													  @RequestParam("file")  MultipartFile[] files)
				throws ResourceNotFoundException, IOException {
	    	  articleService.createNewArticle(referenceIris,numFicheTechnique,designation,typeProduit,idClient,refClient,raisonSocial,files);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		  }

	    @PutMapping("/updateArticle/{articleId}")
		  public ResponseEntity <String> updateArticle(
				  @ApiParam(name = "articleId", value="id of article", required = true)
				  @PathVariable(value = "articleId", required = true) @NotEmpty(message = "{http.error.0001}") String articleId,
				  @RequestParam("referenceIris")@NotEmpty  String referenceIris,
				  @RequestParam("numFicheTechnique") @NotEmpty String numFicheTechnique,
				  @RequestParam("designation") @NotEmpty String designation,
				  @RequestParam("typeProduit")@NotEmpty  String typeProduit,
				  @RequestParam("refClient") @NotEmpty String refClient,
				  @RequestParam("raisonSocial")@NotEmpty  String raisonSocial,
				  @RequestParam("file")MultipartFile[] files) throws ResourceNotFoundException, IOException {
			  
	    	  articleService.updateArticle(referenceIris,numFicheTechnique,designation,typeProduit,refClient,raisonSocial,articleId,files);
		      
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
		  }

	    
		  @DeleteMapping("/deleteArticle/{id}")
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

	@GetMapping("/search")
	@ApiOperation(value = "service to filter personnel ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam int enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return articleService.search(textToFind, page, size,enVeille);

	}
	  
}
	  
	  
	  
	  
	  

