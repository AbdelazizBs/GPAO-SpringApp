package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;


import lombok.Data;

@Data
public class CategorieDto {
	@Id
	private String id;
	
	@Size(max = 100)
	private String code;
	
	@Size(max = 100)
	private String designation;

	private List<ArticleDto> listArticles = new ArrayList<>();
}
