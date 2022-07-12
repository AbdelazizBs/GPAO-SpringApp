package com.housservice.housstock.model.dto;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
	
	@Id
	private String id;
	
	@Size(max = 100)
	private String referenceIris;
	

	@Size(max = 100)
	private String numFicheTechnique;
	

	@Size(max = 100)
	private String designation;
	

	@Size(max = 100)
	private String typeProduit;
	
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateCreationArticle = LocalDate.now();
	
		
}
