package com.housservice.housstock.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Getter
@Setter
public class NomenclatureDto {
	
	/**
	 * TYPE NOMENCLATURE : F
	 */
	@Transient
	public static final String TYPE_FAMILLE = "F";
	/**
	 * TYPE NOMENCLATURE : A
	 */
	@Transient
	public static final String TYPE_ARTICLE = "A";
	/**
	 * TYPE NOMENCLATURE : E
	 */
	@Transient
	public static final String TYPE_ELEMENT = "E";
	
    @Id
    private String id;

    @NotBlank
    private String idCompte;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String nom;
    

    private String description;
    
    @NotBlank
    private String type;
    
    private String idParent;
    
    private List<NomenclatureDto> listNomenclatureChildren = new ArrayList<>();
    
}
