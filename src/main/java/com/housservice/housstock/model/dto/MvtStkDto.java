package com.housservice.housstock.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MvtStkDto {

	@Id
	private String id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateMvt;

	@Size(max = 100)
	private BigDecimal quantite;
	
	@Size(max = 100)
	private String typeMvt;

	private String idArticle;
	
	private String designationArticle;
	
}
