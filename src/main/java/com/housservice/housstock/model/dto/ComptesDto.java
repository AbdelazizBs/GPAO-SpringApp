package com.housservice.housstock.model.dto;

import javax.validation.constraints.Size;

import com.housservice.housstock.model.Roles;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ComptesDto {
	
	    private String id;

	    @Size(max = 100)
	    private String email;
	    
	    @Size(max = 100)
	    private String password;
	    
	private List<String> roles;


	public ComptesDto() {

	}
}
