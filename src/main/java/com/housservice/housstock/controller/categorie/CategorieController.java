package com.housservice.housstock.controller.categorie;

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
import com.housservice.housstock.model.dto.CategorieDto;
import com.housservice.housstock.service.CategorieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Categories Management"})
public class CategorieController {
	
	private CategorieService categorieService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	@Autowired
	public CategorieController(CategorieService categorieService , MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.categorieService = categorieService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}
		
	  @GetMapping("/categorie")
		 public List< CategorieDto > getAllCategorie() {
			 		
			 return categorieService.getAllCategorie();
			 	 
		 }
	  
	  @GetMapping("/categorie/{id}")
	  @ApiOperation(value = "service to get one Categorie by Id.")
	  public ResponseEntity < CategorieDto > getCategorieById(
			  @ApiParam(name = "id", value="id of categorie", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String categorieId)
	  throws ResourceNotFoundException {
    	CategorieDto categorie = categorieService.getCategorieById(categorieId);
		  if (categorie == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(categorie);
	  }
	  
	  @PutMapping("/categorie")
	  public ResponseEntity<String> createCategorie(@Valid @RequestBody CategorieDto categorieDto) {
		  
    	  categorieService.createNewCategorie(categorieDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
	  
	    @PutMapping("/categorie/{id}")
			  public ResponseEntity <String> updateCategorie(
					  @ApiParam(name = "id", value="id of categorie", required = true)
					  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String categorieId,
			          @Valid @RequestBody(required = true) CategorieDto categorieDto) throws ResourceNotFoundException {
				  
		    	  categorieService.updateCategorie(categorieDto);
			      
			      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
			  }
	  
		  @DeleteMapping("/categorie/{id}")
		  @ApiOperation(value = "service to delete one Categorie by Id.")
		  public Map < String, Boolean > deleteCategorie(
				  @ApiParam(name = "id", value="id of categorie", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String categorieId) {

			  categorieService.deleteCategorie(categorieId);
		      Map < String, Boolean > response = new HashMap < > ();
		      response.put("deleted", Boolean.TRUE);
		      return response;
		  }
	  	

}
