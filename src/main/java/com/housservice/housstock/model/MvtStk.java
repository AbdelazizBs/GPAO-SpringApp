package com.housservice.housstock.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 

@Document(collection="MvtStk")
public class MvtStk{
	
	@Transient
	public static final String SEQUENCE_NAME ="mvtStk_sequence";
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dateMvt;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private BigDecimal quantite;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String typeMvt;

	private Article article;
	
}
