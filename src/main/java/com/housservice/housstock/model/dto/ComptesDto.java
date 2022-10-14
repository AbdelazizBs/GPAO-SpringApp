package com.housservice.housstock.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.housservice.housstock.model.Roles;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ComptesDto {
	
	    @Id
	    private String id;

	    @Size(max = 100)
	    private String email;
	    
	    @Size(max = 100)
	    private String password;
	    
	private List<Roles> roles = new ArrayList<>();



}
